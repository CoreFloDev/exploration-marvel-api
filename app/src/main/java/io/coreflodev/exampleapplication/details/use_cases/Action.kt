package io.coreflodev.exampleapplication.details.use_cases

sealed class Action {

    data class InitialAction(val postId: String): Action()
}
