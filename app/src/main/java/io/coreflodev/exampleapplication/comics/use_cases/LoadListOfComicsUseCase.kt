package io.coreflodev.exampleapplication.comics.use_cases

import io.coreflodev.exampleapplication.comics.repo.ComicsRepository
import io.reactivex.ObservableTransformer

class LoadListOfComicsUseCase(private val comicsRepository: ComicsRepository) {

    operator fun invoke(): ObservableTransformer<Action.InitialAction, Result> = ObservableTransformer { observable ->
        observable.flatMap {
            comicsRepository.getListOfPosts()
                .map<Result> { Result.UiUpdate.Display(it) }
                .onErrorReturnItem(Result.UiUpdate.Error)
                .startWith(Result.UiUpdate.Loading)
        }
    }

}

