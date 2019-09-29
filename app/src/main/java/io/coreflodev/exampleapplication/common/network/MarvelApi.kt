package io.coreflodev.exampleapplication.common.network

import io.coreflodev.exampleapplication.common.network.comics.ComicDataWrapper
import io.reactivex.Single
import retrofit2.http.GET

interface MarvelApi {

    @GET("/v1/public/comics")
    fun getComics(): Single<ComicDataWrapper>
}


