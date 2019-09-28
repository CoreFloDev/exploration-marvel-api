package io.coreflodev.exampleapplication.comics.arch

import io.coreflodev.exampleapplication.common.arch.ScreenInput

sealed class ComicsInput : ScreenInput {

    data class ItemClicked(val id: String) : ComicsInput()

    object Retry : ComicsInput()
}
