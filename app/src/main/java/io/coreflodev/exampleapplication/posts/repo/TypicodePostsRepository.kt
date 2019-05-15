package io.coreflodev.exampleapplication.posts.repo

import io.coreflodev.exampleapplication.common.network.TypicodeApi
import io.coreflodev.exampleapplication.posts.injection.PostsScope
import io.reactivex.Observable
import javax.inject.Inject

@PostsScope
class TypicodePostsRepository @Inject constructor(
    private val typicodeApi: TypicodeApi
) : PostsRepository {

    override fun getListOfPosts(): Observable<List<PostsRepository.Post>> =
        typicodeApi
            .getPosts()
            .toObservable()
            .map { postsList ->
                postsList.map {
                    PostsRepository.Post(
                        id = it.id,
                        content = it.body
                    )
                }
            }

}
