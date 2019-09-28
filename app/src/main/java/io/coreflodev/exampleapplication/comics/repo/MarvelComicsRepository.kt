package io.coreflodev.exampleapplication.comics.repo

import io.coreflodev.exampleapplication.common.network.MarvelApi
import io.reactivex.Observable

class MarvelComicsRepository(
    private val marvelApi: MarvelApi
) : ComicsRepository {

    override fun getListOfPosts(): Observable<List<ComicsRepository.Post>> =
        marvelApi
            .getPosts()
            .toObservable()
            .map { postsList ->
                postsList.map {
                    ComicsRepository.Post(
                        id = it.id,
                        content = it.body
                    )
                }
            }
            .doOnError {
                println("error received $it")
            }

}
