package io.coreflodev.exampleapplication.comics.arch

import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.comics.use_cases.Action
import io.coreflodev.exampleapplication.comics.use_cases.LoadListOfComicsUseCase
import io.coreflodev.exampleapplication.comics.use_cases.NavigateToDetailsUseCase
import io.coreflodev.exampleapplication.comics.use_cases.Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class ComicsScreen(
    private val loadListOfComicsUseCase: LoadListOfComicsUseCase,
    private val navigateToDetailsUseCase: NavigateToDetailsUseCase
) : Screen<ComicsInput, ComicsOutput>() {

    override fun output(): Observable<ComicsOutput> = input.compose(
        convertInputToAction()
    )
        .publish {
            Observable.mergeArray(
                it.ofType(Action.ItemClicked::class.java).compose(navigateToDetailsUseCase()),
                it.ofType(Action.InitialAction::class.java).compose(loadListOfComicsUseCase())
            )
        }
        .compose(convertResultToOutput())

    companion object {

        fun convertInputToAction() = ObservableTransformer<ComicsInput, Action> { observable ->
            observable.map { input ->
                when (input) {
                    is ComicsInput.ItemClicked -> Action.ItemClicked(input.id)
                    ComicsInput.Retry -> Action.InitialAction
                }
            }
                .startWith(Action.InitialAction)
        }

        fun convertResultToOutput() = ObservableTransformer<Result, ComicsOutput> { observable ->
            val upstream = observable
                .publish()
                .autoConnect()

            val models = upstream.ofType(Result.UiUpdate::class.java)
                .compose(reducingUiState())
                .replay(1)
                .autoConnect()

            val navigation = upstream.ofType(Result.Navigation::class.java)
                .compose(reducingNavigation())

            Observable.merge(models, navigation)
        }

        fun reducingUiState() = ObservableTransformer<Result.UiUpdate, ComicsOutput> { observable ->
            observable.map { uiState ->
                when (uiState) {
                    Result.UiUpdate.Error -> ComicsOutput.Error
                    Result.UiUpdate.Loading -> ComicsOutput.Loading
                    is Result.UiUpdate.Display -> ComicsOutput.Display(
                        uiState.data.map {
                            ComicsViewModel(
                                it.id,
                                it.content,
                                it.thumbnail
                            )
                        })
                }
            }
        }

        fun reducingNavigation() = ObservableTransformer<Result.Navigation, ComicsOutput> { observable ->
            observable.map { navigationState ->
                when (navigationState) {
                    is Result.Navigation.ToDetails -> ComicsOutput.ToDetail(
                        navigationState.id
                    )
                }
            }
        }
    }
}
