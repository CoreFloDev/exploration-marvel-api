package io.coreflodev.exampleapplication.comics.use_cases

import io.coreflodev.exampleapplication.comics.repo.ComicsRepository

sealed class Result {

    sealed class UiUpdate : Result() {
        object Error : UiUpdate()

        object Loading : UiUpdate()

        data class Display(val data: List<ComicsRepository.Comics>) : UiUpdate()
    }

    sealed class Navigation : Result() {
        data class ToDetails(val id: String) : Navigation()
    }
}
