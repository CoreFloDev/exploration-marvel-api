package io.coreflodev.exampleapplication.posts.repo

import io.coreflodev.exampleapplication.common.network.TypicodeApi
import io.reactivex.Observable

class TypicodePostsRepository(
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
