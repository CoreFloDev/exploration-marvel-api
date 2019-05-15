package io.coreflodev.exampleapplication.list

import io.coreflodev.exampleapplication.common.arch.ScreenOutput

sealed class ListOutput : ScreenOutput {

    data class Display(val data: List<String>) : ListOutput()

    object Loading : ListOutput()

    object Error : ListOutput()

    data class ToDetail(val id: String) : ListOutput()

}