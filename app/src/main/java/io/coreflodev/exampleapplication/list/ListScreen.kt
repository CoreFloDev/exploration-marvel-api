package io.coreflodev.exampleapplication.list

import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.arch.ScreenView
import io.coreflodev.exampleapplication.list.injection.ListScope
import io.coreflodev.exampleapplication.list.repo.ListRepository
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

@ListScope
class ListScreen @Inject constructor(
    private val listRepository: ListRepository
) : Screen<ListInput, ListOutput>() {

    private val input: Subject<ListInput> = PublishSubject.create()
    private val output: Observable<ListOutput>

    init {
        output = input.compose(convertInputToAction())
            .publish {
                Observable.mergeArray(
                    it.ofType(Action.ItemClicked::class.java).compose(navigateToDetails()),
                    it.ofType(Action.InitialAction::class.java).compose(loadListOfPost(listRepository))
                )
            }
            .compose(convertResultToOutput())

    }

    override fun onAttach(view: ScreenView<ListInput, ListOutput>) {
        add(
            output.observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::render),

            view.inputs()
                .subscribe(input::onNext)
        )
    }


    sealed class Action {
        data class ItemClicked(val id: String) : Action()

        object InitialAction : Action()
    }

    sealed class Result {

        sealed class UiUpdate : Result() {
            object Error : UiUpdate()

            object Loading : UiUpdate()

            data class Display(val data: List<ListRepository.Post>) : UiUpdate()
        }

        sealed class Navigation : Result() {
            data class ToDetails(val id: String) : Navigation()
        }
    }

    companion object {

        fun convertInputToAction() = ObservableTransformer<ListInput, Action> { observable ->
            observable.map { input ->
                when (input) {
                    is ListInput.ItemClicked -> Action.ItemClicked(input.id)
                } as Action
            }
                .startWith(Action.InitialAction)
        }

        fun navigateToDetails() = ObservableTransformer<Action.ItemClicked, Result> { observable ->
            observable.map {
                Result.Navigation.ToDetails(it.id)
            }
        }

        fun convertResultToOutput() = ObservableTransformer<Result, ListOutput> { observable ->
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

        fun reducingUiState() = ObservableTransformer<Result.UiUpdate, ListOutput> { observable ->
            observable.map { uiState ->
                when (uiState) {
                    Result.UiUpdate.Error -> ListOutput.Error
                    Result.UiUpdate.Loading -> ListOutput.Loading
                    is Result.UiUpdate.Display -> ListOutput.Display(uiState.data.map {
                        PostViewModel(
                            it.id,
                            it.content
                        )
                    })
                }
            }
        }

        fun reducingNavigation() = ObservableTransformer<Result.Navigation, ListOutput> { observable ->
            observable.map { navigationState ->
                when (navigationState) {
                    is Result.Navigation.ToDetails -> ListOutput.ToDetail(navigationState.id)
                }
            }
        }

        fun loadListOfPost(listRepository: ListRepository) =
            ObservableTransformer<Action.InitialAction, Result> { observable ->
                observable.flatMap {
                    listRepository.getListOfPosts()
                        .map { Result.UiUpdate.Display(it) as Result }
                        //.onErrorReturn { Result.UiUpdate.Error }
                        .startWith(Result.UiUpdate.Loading)

                }
            }
    }
}
