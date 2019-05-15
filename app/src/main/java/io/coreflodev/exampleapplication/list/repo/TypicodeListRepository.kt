package io.coreflodev.exampleapplication.list.repo

import io.coreflodev.exampleapplication.common.network.TypicodeApi
import io.coreflodev.exampleapplication.list.injection.ListScope
import io.reactivex.Observable
import javax.inject.Inject

@ListScope
class TypicodeListRepository @Inject constructor(
    private val typicodeApi: TypicodeApi
) : ListRepository {

    override fun getListOfPosts(): Observable<List<ListRepository.Post>> =
        typicodeApi
            .getPosts()
            .toObservable()
            .map { postsList ->
                postsList.map {
                    ListRepository.Post(
                        id = it.id,
                        content = it.body
                    )
                }
            }

}
