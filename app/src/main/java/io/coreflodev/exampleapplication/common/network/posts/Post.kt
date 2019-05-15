package io.coreflodev.exampleapplication.common.network.posts

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post(
    val userId : String,
    val id : String,
    val title: String,
    val body: String
)