package io.coreflodev.exampleapplication.details.use_cases

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.coreflodev.exampleapplication.details.repo.DetailsRepository
import io.reactivex.Observable
import org.junit.Test

class DisplayComicsDetailsUseCaseTest {

    private val repo: DetailsRepository = mock()
    private val useCase = DisplayPostDetailsUseCase(repo)

    @Test
    fun `given initial action received when repo is empty then a loading state is received`() {
        whenever(repo.getPostForId(POST_ID)).thenReturn(Observable.empty())

        Observable.just(Action.InitialAction(POST_ID))
            .compose(useCase())
            .test()
            .assertValue(Result.UiUpdate.Loading)
    }

    @Test
    fun `given initial action is received when repo return an error then a loading and an error state is received`() {
        whenever(repo.getPostForId(POST_ID)).thenReturn(Observable.error(Throwable()))

        Observable.just(Action.InitialAction(POST_ID))
            .compose(useCase())
            .test()
            .assertValues(Result.UiUpdate.Loading, Result.UiUpdate.Error)
    }

    @Test
    fun `given initial action is received when repo return a result then a loading and a display state is returned`() {
        whenever(repo.getPostForId(POST_ID)).thenReturn(Observable.just(A_POST))

        Observable.just(Action.InitialAction(POST_ID))
            .compose(useCase())
            .test()
            .assertValues(Result.UiUpdate.Loading, Result.UiUpdate.Display(A_POST))
    }

    companion object {
        private const val POST_ID = "post_id"

        private val A_POST = DetailsRepository.Post(
            title = "TITLE",
            body = "BODY",
            numberOfComments = "456",
            userName = "hello"
        )
    }
}
