package io.coreflodev.exampleapplication.comics.repo

import io.coreflodev.exampleapplication.common.network.MarvelApi
import io.reactivex.Observable

class MarvelComicsRepository(
    private val marvelApi: MarvelApi
) : ComicsRepository {

    override fun getListOfComics(): Observable<List<ComicsRepository.Comics>> =
        marvelApi
            .getComics()
            .toObservable()
            .map { comics ->
                comics.data?.results.orEmpty()
                    .filter { it.title != null && it.id != null && it.thumbnail?.path != null && it.thumbnail.extension != null }
                    .map {
                        ComicsRepository.Comics(
                            id = it.id!!,
                            content = it.title!!,
                            thumbnail = "${it.thumbnail?.path!!}.${it.thumbnail.extension!!}"
                        )
                }
            }

}
