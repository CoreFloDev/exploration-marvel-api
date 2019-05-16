package io.coreflodev.exampleapplication.details.use_cases

import io.coreflodev.exampleapplication.details.repo.DetailsRepository
import io.reactivex.ObservableTransformer

class DisplayPostDetailsUseCase(private val repo: DetailsRepository) {

    operator fun invoke(): ObservableTransformer<Action.InitialAction, Result> = ObservableTransformer {
        it.flatMap {
            repo.getPostForId(it.postId)
                .map {
                    Result.Display(it):
                }
        }
    }
}
