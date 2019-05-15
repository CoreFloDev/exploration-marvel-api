package io.coreflodev.exampleapplication.list

import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.arch.ScreenView
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class ListScreen(
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
            .compose()

    }

    override fun onAttach(view: ScreenView<ListInput, ListOutput>) {

    }


    sealed class Action {
        data class ItemClicked(val id: String) : Action()

        object InitialAction : Action()
    }

    sealed class Result {

        sealed class UiUpdate : Result() {
            object Error : UiUpdate()

            object Loading : UiUpdate()

            data class Display(val data: List<String>) : UiUpdate()
        }

        sealed class Navigation : Result() {
            data class ToDetails(val id: String) : Navigation()
        }
    }

    companion object {

        fun convertInputToAction() = ObservableTransformer<ListInput, Action> {
            it
                .map { input ->
                    when (input) {
                        is ListInput.ItemClicked -> Action.ItemClicked(input.id)
                    } as Action
                }
                .startWith(Action.InitialAction)
        }

        fun navigateToDetails() = ObservableTransformer<Action.ItemClicked, Result> {
            it
                .map {
                    Result.Navigation.ToDetails(it.id)
                }
        }

        fun loadListOfPost(listRepository: ListRepository) = ObservableTransformer<Action.InitialAction, Result> {
            it
                .flatMap {
                    listRepository.getList()
                        .map { Result.UiUpdate.Display(listOf("test")) as Result }
                        .onErrorReturn { Result.UiUpdate.Error }
                        .startWith(Result.UiUpdate.Loading)

                }
        }
    }
}