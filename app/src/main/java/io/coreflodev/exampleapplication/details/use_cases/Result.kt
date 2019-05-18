package io.coreflodev.exampleapplication.details.use_cases

import io.coreflodev.exampleapplication.details.repo.DetailsRepository

sealed class Result {

    sealed class UiUpdate : Result() {

        data class Display(val data: DetailsRepository.Post) : UiUpdate()

        object Error : UiUpdate()

        object Loading : UiUpdate()
    }
}
