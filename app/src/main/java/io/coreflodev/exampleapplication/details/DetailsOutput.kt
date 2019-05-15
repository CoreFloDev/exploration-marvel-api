package io.coreflodev.exampleapplication.details

import io.coreflodev.exampleapplication.common.arch.ScreenOutput

sealed class DetailsOutput : ScreenOutput {

    data class Display(val data: DetailsViewModel) : DetailsOutput()

    object Loading : DetailsOutput()

    object Error : DetailsOutput()
}