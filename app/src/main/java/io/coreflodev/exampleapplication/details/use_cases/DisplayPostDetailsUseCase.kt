package io.coreflodev.exampleapplication.details.use_cases

import io.coreflodev.exampleapplication.details.repo.DetailsRepository
import io.reactivex.ObservableTransformer

class DisplayPostDetailsUseCase(private val repo: DetailsRepository) {

    operator fun invoke(): ObservableTransformer<Action.InitialAction, Result> = ObservableTransformer { observable ->
        observable.flatMap { action ->
            repo.getPostForId(action.postId)
                .map { Result.UiUpdate.Display(it) as Result }
                //.onErrorReturnItem(Result.UiUpdate.Error)
                .startWith(Result.UiUpdate.Loading)
        }
    }
}
