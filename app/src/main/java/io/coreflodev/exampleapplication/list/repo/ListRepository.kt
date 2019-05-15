package io.coreflodev.exampleapplication.list.repo

import io.reactivex.Observable

interface ListRepository {

    fun getListOfPosts(): Observable<List<Post>>

    data class Post(val id: String, val content: String)
}
