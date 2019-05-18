package io.coreflodev.exampleapplication.posts

import io.coreflodev.exampleapplication.common.arch.ScreenInput

sealed class PostsInput : ScreenInput {

    data class ItemClicked(val id: String) : PostsInput()

    object Retry : PostsInput()
}
