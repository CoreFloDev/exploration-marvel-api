package io.coreflodev.exampleapplication.posts.repo

import io.reactivex.Observable

interface PostsRepository {

    fun getListOfPosts(): Observable<List<Post>>

    data class Post(val id: String, val content: String)
}
