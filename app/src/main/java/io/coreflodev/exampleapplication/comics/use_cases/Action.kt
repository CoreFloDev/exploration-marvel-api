package io.coreflodev.exampleapplication.comics.use_cases

sealed class Action {
    data class ItemClicked(val id: String) : Action()

    object InitialAction : Action()
}
