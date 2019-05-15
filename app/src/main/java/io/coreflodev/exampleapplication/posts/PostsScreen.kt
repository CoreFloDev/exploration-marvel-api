package io.coreflodev.exampleapplication.posts

import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.arch.ScreenView
import io.coreflodev.exampleapplication.posts.use_cases.Action
import io.coreflodev.exampleapplication.posts.use_cases.LoadListOfPostsUseCase
import io.coreflodev.exampleapplication.posts.use_cases.NavigateToDetailsUseCase
import io.coreflodev.exampleapplication.posts.use_cases.Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class PostsScreen(
    loadListOfPostsUseCase: LoadListOfPostsUseCase,
    navigateToDetailsUseCase: NavigateToDetailsUseCase
) : Screen<PostsInput, PostsOutput>() {

    private val input: Subject<PostsInput> = PublishSubject.create()
    private val output: Observable<PostsOutput>

    init {
        output = input.compose(convertInputToAction())
            .publish {
                Observable.mergeArray(
                    it.ofType(Action.ItemClicked::class.java).compose(navigateToDetailsUseCase()),
                    it.ofType(Action.InitialAction::class.java).compose(loadListOfPostsUseCase())
                )
            }
            .compose(convertResultToOutput())
    }

    override fun onAttach(view: ScreenView<PostsInput, PostsOutput>) {
        add(
            output.observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::render),

            view.inputs()
                .subscribe(input::onNext)
        )
    }

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
