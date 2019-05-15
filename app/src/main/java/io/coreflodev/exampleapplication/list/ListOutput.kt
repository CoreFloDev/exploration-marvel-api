package io.coreflodev.exampleapplication.list

import io.coreflodev.exampleapplication.common.arch.ScreenOutput

sealed class ListOutput : ScreenOutput {

    data class Display(val data: List<PostViewModel>) : ListOutput()

    object Loading : ListOutput()

    object Error : ListOutput()

    data class ToDetail(val id: String) : ListOutput()

}

data class PostViewModel(
    val id: String,
    val content: String
)
