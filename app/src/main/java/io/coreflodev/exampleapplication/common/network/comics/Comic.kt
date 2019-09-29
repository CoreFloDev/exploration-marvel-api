package io.coreflodev.exampleapplication.common.network.comics

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comic(
    val id: String? = null,
    val title: String? = null,
    val thumbnail: Image? = null
)
