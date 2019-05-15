package io.coreflodev.exampleapplication.common.network.users

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val id: String,
    val name: String,
    val username: String,
    val phone: String,
    val website: String
)