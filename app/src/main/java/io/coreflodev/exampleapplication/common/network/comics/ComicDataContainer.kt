package io.coreflodev.exampleapplication.common.network.comics

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicDataContainer(
    val results: List<Comic> = emptyList()
)
