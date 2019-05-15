package io.coreflodev.exampleapplication.posts

import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.arch.ScreenView
import io.coreflodev.exampleapplication.posts.injection.PostsScope
import io.coreflodev.exampleapplication.posts.repo.PostsRepository
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

@PostsScope
class PostsScreen @Inject constructor(
    private val postsRepository: PostsRepository
) : Screen<PostsInput, PostsOutput>() {

    private val input: Subject<PostsInput> = PublishSubject.create()
    private val output: Observable<PostsOutput>

    init {
        output = input.compose(convertInputToAction())
            .publish {
                Observable.mergeArray(
                    it.ofType(Action.ItemClicked::class.java).compose(navigateToDetails()),
                    it.ofType(Action.InitialAction::class.java).compose(loadListOfPost(postsRepository))
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


    sealed class Action {
        data class ItemClicked(val id: String) : Action()

        object InitialAction : Action()
    }

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

    companion object {

        fun convertInputToAction() = ObservableTransformer<PostsInput, Action> { observable ->
            observable.map { input ->
                when (input) {
                    is PostsInput.ItemClicked -> Action.ItemClicked(input.id)
                } as Action
            }
                .startWith(Action.InitialAction)
        }

        fun navigateToDetails() = ObservableTransformer<Action.ItemClicked, Result> { observable ->
            observable.map {
                Result.Navigation.ToDetails(it.id)
            }
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

        fun loadListOfPost(postsRepository: PostsRepository) =
            ObservableTransformer<Action.InitialAction, Result> { observable ->
                observable.flatMap {
                    postsRepository.getListOfPosts()
                        .map { Result.UiUpdate.Display(it) as Result }
                        .onErrorReturn { Result.UiUpdate.Error }
                        .startWith(Result.UiUpdate.Loading)

                }
            }
    }
}
