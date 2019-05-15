package io.coreflodev.exampleapplication.list

import io.coreflodev.exampleapplication.common.arch.ScreenInput

sealed class ListInput : ScreenInput {

    data class ItemClicked(val id: String) : ListInput()

}