package io.coreflodev.exampleapplication.posts.use_cases

import io.coreflodev.exampleapplication.posts.repo.PostsRepository
import io.reactivex.ObservableTransformer

class LoadListOfPostsUseCase(private val postsRepository: PostsRepository) {

    operator fun invoke(): ObservableTransformer<Action.InitialAction, Result> = ObservableTransformer { observable ->
        observable.flatMap {
            postsRepository.getListOfPosts()
                .map<Result> { Result.UiUpdate.Display(it) }
                .onErrorReturnItem(Result.UiUpdate.Error)
                .startWith(Result.UiUpdate.Loading)
        }
    }

}

