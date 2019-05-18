package io.coreflodev.exampleapplication.details

import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.arch.ScreenView
import io.coreflodev.exampleapplication.details.use_cases.Action
import io.coreflodev.exampleapplication.details.use_cases.DisplayPostDetailsUseCase
import io.coreflodev.exampleapplication.details.use_cases.Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class DetailsScreen(
    postId: String,
    displayPostDetailsUseCase: DisplayPostDetailsUseCase
) : Screen<DetailsInput, DetailsOutput>() {

    private val input: Subject<DetailsInput> = PublishSubject.create()
    private val output: Observable<DetailsOutput>

    init {
        output = input.compose(convertInputToAction(postId))
            .doOnNext {
                println("action $it")
            }
            .publish {
                it.ofType(Action.InitialAction::class.java).compose(displayPostDetailsUseCase())
            }
            .compose(convertResultToOutput())
    }

    override fun onAttach(view: ScreenView<DetailsInput, DetailsOutput>) {
        add(
            output.observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::render),

            view.inputs()
                .subscribe(input::onNext)
        )
    }

    companion object {
        fun convertInputToAction(postId: String) = ObservableTransformer<DetailsInput, Action> { observable ->
            observable
                .map { it as Action } // no input expected
                .startWith(Action.InitialAction(postId))
        }

        fun convertResultToOutput() = ObservableTransformer<Result, DetailsOutput> { observable ->
            observable.ofType(Result.UiUpdate::class.java)
                .compose(reducingUiState())
                .replay(1)
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