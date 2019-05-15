package io.coreflodev.exampleapplication.posts.use_cases

import io.reactivex.ObservableTransformer

class NavigateToDetailsUseCase {

    operator fun invoke(): ObservableTransformer<Action.ItemClicked, Result> = ObservableTransformer { observable ->
        observable.map {
            Result.Navigation.ToDetails(it.id)
        }
    }
}
