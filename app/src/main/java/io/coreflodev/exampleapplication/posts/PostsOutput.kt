package io.coreflodev.exampleapplication.posts

import io.coreflodev.exampleapplication.common.arch.ScreenOutput

sealed class PostsOutput : ScreenOutput {

    data class Display(val data: List<PostsViewModel>) : PostsOutput()

    object Loading : PostsOutput()

    object Error : PostsOutput()

    data class ToDetail(val id: String) : PostsOutput()

}
