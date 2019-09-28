package io.coreflodev.exampleapplication.comics.repo

import io.reactivex.Observable

interface ComicsRepository {

    fun getListOfPosts(): Observable<List<Post>>

    data class Post(val id: String, val content: String)
}
