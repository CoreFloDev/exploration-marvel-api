package io.coreflodev.exampleapplication.details.repo

import io.coreflodev.exampleapplication.common.network.TypicodeApi
import io.reactivex.Observable

class TypicodeDetailsRepository(private val typicodeApi: TypicodeApi) : DetailsRepository {

    override fun getPostForId(id: String): Observable<DetailsRepository.Post> {
        return typicodeApi.getPosts()
            .toObservable()
            .flatMapIterable { it }
            .filter { it.id == id }
            .singleOrError()
            .flatMap { post ->
                typicodeApi.getUsers()
                    .toObservable()
                    .flatMapIterable { it }
                    .filter { it.id == post.userId }
                    .singleOrError()
                    .map { it.username to post }
            }
            .flatMap { (username, post) ->
                typicodeApi.getComments()
                    .toObservable()
                    .flatMapIterable { it }
                    .filter { it.postId == post.id }
                    .count()
                    .map {
                        DetailsRepository.Post(
                            title = post.title,
                            body = post.body,
                            userName = username,
                            numberOfComments = it.toString()
                        )
                    }
            }
            .toObservable()
    }

}
