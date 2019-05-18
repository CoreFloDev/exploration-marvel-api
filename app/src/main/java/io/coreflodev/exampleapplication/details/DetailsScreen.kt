package io.coreflodev.exampleapplication.details

import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.details.use_cases.Action
import io.coreflodev.exampleapplication.details.use_cases.DisplayPostDetailsUseCase
import io.coreflodev.exampleapplication.details.use_cases.Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class DetailsScreen(
    private val postId: String,
    private val displayPostDetailsUseCase: DisplayPostDetailsUseCase
) : Screen<DetailsInput, DetailsOutput>() {

    override fun output(): Observable<DetailsOutput> =
        input.compose(convertInputToAction(postId))
            .publish {
                it.ofType(Action.InitialAction::class.java).compose(displayPostDetailsUseCase())
            }
            .compose(convertResultToOutput())

    companion object {
        fun convertInputToAction(postId: String) = ObservableTransformer<DetailsInput, Action> { observable ->
            observable
                .map { Action.InitialAction(postId) as Action } // no input expected
                .startWith(Action.InitialAction(postId))
        }

        fun convertResultToOutput() = ObservableTransformer<Result, DetailsOutput> { observable ->
            observable.ofType(Result.UiUpdate::class.java)
                .compose(reducingUiState())
                .replay(1)
                .autoConnect()
        }

        fun reducingUiState() = ObservableTransformer<Result.UiUpdate, DetailsOutput> { observable ->
            observable.map { uiState ->
                when (uiState) {
                    Result.UiUpdate.Error -> DetailsOutput.Error
                    is Result.UiUpdate.Display -> DetailsOutput.Display(
                        data = DetailsViewModel(
                            postTitle = uiState.data.title,
                            postBody = uiState.data.body,
                            userName = uiState.data.userName,
                            numberOfComments = uiState.data.numberOfComments
                        )
                    )
                    Result.UiUpdate.Loading -> DetailsOutput.Error
                }
            }
        }
    }
}