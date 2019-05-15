package io.coreflodev.exampleapplication.common.network

import com.squareup.moshi.JsonClass
import io.reactivex.Single
import retrofit2.http.GET

interface TypicodeApi {

    @GET("posts")
    fun getPosts() : Single<List<Post>>

    @JsonClass(generateAdapter = true)
    data class Post(
        val userId : String,
        val id : String,
        val title: String,
        val body: String
    )
}


