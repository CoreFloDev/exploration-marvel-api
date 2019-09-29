package io.coreflodev.exampleapplication.comics.repo

import io.reactivex.Observable

interface ComicsRepository {

    fun getListOfComics(): Observable<List<Comics>>

    data class Comics(val id: String, val content: String)
}
