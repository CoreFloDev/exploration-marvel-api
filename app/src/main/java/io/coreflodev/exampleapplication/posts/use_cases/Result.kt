package io.coreflodev.exampleapplication.posts.use_cases

import io.coreflodev.exampleapplication.posts.repo.PostsRepository

sealed class Result {

    sealed class UiUpdate : Result() {
        object Error : UiUpdate()

        object Loading : UiUpdate()

        data class Display(val data: List<PostsRepository.Post>) : UiUpdate()
    }

    sealed class Navigation : Result() {
        data class ToDetails(val id: String) : Navigation()
    }
}
