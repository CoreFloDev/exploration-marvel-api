package io.coreflodev.exampleapplication.posts.use_cases

import io.reactivex.Observable
import org.junit.Test

class NavigateToDetailsUseCaseTest {

    private val useCase = NavigateToDetailsUseCase()

    @Test
    fun `given an action item clicked is received when everything is fine then a navigation event is received`() {
        Observable.just(Action.ItemClicked(ID))
            .compose(useCase())
            .test()
            .assertValue(Result.Navigation.ToDetails(ID))
    }

    companion object {
        private const val ID = "myId"
    }
}