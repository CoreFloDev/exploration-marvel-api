package io.coreflodev.exampleapplication.comics.arch

import io.coreflodev.exampleapplication.common.arch.ScreenOutput

sealed class ComicsOutput : ScreenOutput {

    data class Display(val data: List<ComicsViewModel>) : ComicsOutput()

    object Loading : ComicsOutput()

    object Error : ComicsOutput()

    data class ToDetail(val id: String) : ComicsOutput()

}
