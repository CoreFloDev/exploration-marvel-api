package io.coreflodev.exampleapplication.common.network

import io.coreflodev.exampleapplication.common.network.comments.Comment
import io.coreflodev.exampleapplication.common.network.posts.Post
import io.coreflodev.exampleapplication.common.network.users.User
import io.reactivex.Single
import retrofit2.http.GET

interface MarvelApi {

    @GET("posts")
    fun getPosts() : Single<List<Post>>

    @GET("users")
    fun getUsers() : Single<List<User>>

    @GET("comments")
    fun getComments(): Single<List<Comment>>
}


