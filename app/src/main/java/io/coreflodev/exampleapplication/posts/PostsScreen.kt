package io.coreflodev.exampleapplication.posts

import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.posts.use_cases.Action
import io.coreflodev.exampleapplication.posts.use_cases.LoadListOfPostsUseCase
import io.coreflodev.exampleapplication.posts.use_cases.NavigateToDetailsUseCase
import io.coreflodev.exampleapplication.posts.use_cases.Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class PostsScreen(
    private val loadListOfPostsUseCase: LoadListOfPostsUseCase,
    private val navigateToDetailsUseCase: NavigateToDetailsUseCase
) : Screen<PostsInput, PostsOutput>() {

    override fun output(): Observable<PostsOutput> = input.compose(convertInputToAction())
        .publish {
            Observable.mergeArray(
                it.ofType(Action.ItemClicked::class.java).compose(navigateToDetailsUseCase()),
                it.ofType(Action.InitialAction::class.java).compose(loadListOfPostsUseCase())
            )
        }
        .compose(convertResultToOutput())


    companion object {

        fun convertInputToAction() = ObservableTransformer<PostsInput, Action> { observable ->
            observable.map { input ->
                when (input) {
                    is PostsInput.ItemClicked -> Action.ItemClicked(input.id)
                } as Action
            }
                .startWith(Action.InitialAction)
        }

        fun convertResultToOutput() = ObservableTransformer<Result, PostsOutput> { observable ->
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

        fun reducingUiState() = ObservableTransformer<Result.UiUpdate, PostsOutput> { observable ->
            observable.map { uiState ->
                when (uiState) {
                    Result.UiUpdate.Error -> PostsOutput.Error
                    Result.UiUpdate.Loading -> PostsOutput.Loading
                    is Result.UiUpdate.Display -> PostsOutput.Display(uiState.data.map {
                        PostsViewModel(
                            it.id,
                            it.content
                        )
                    })
                }
            }
        }

        fun reducingNavigation() = ObservableTransformer<Result.Navigation, PostsOutput> { observable ->
            observable.map { navigationState ->
                when (navigationState) {
                    is Result.Navigation.ToDetails -> PostsOutput.ToDetail(navigationState.id)
                }
            }
        }
    }
}
