package io.coreflodev.exampleapplication.common.network.comments

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comment(
    val postId: String,
    val id: String,
    val name: String,
    val email: String,
    val body: String
)