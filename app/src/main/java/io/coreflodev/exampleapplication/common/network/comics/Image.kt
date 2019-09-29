package io.coreflodev.exampleapplication.common.network.comics

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    val path: String? = null,
    val extension: String? = null
)
