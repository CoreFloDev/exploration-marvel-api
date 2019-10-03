package io.coreflodev.exampleapplication.comics.repo

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.coreflodev.exampleapplication.common.network.MarvelApi
import io.coreflodev.exampleapplication.common.network.comics.Comic
import io.coreflodev.exampleapplication.common.network.comics.ComicDataContainer
import io.coreflodev.exampleapplication.common.network.comics.ComicDataWrapper
import io.coreflodev.exampleapplication.common.network.comics.Image
import io.reactivex.Single
import org.junit.Test

class MarvelComicsRepositoryTest {

    private val apiMock: MarvelApi = mock()
    private val repo = MarvelComicsRepository(apiMock)

    @Test
    fun `when the api return an error then the error is received`() {
        val expectedError = Throwable()
        whenever(apiMock.getComics()).thenReturn(Single.error(expectedError))

        repo.getListOfComics()
            .test()
            .assertError(expectedError)
    }

    @Test
    fun `when the api return a list of post then that list is transformed into an expected object`() {
        whenever(apiMock.getComics()).thenReturn(Single.just(AN_API_COMICS))

        repo.getListOfComics()
            .test()
            .assertValue(listOf(EXPECTED_POST))
    }

    companion object {

        private const val AN_ID = "anId"
        private const val A_MARVEL_TITLE = "marvelTitle"
        private const val PATH = "http://perdu.com/image"
        private const val EXTENSION = "png"

        private val AN_API_COMICS = ComicDataWrapper(
            data = ComicDataContainer(
                results = listOf(
                    Comic(
                        id = AN_ID,
                        title = A_MARVEL_TITLE,
                        thumbnail = Image(
                            path = PATH,
                            extension = EXTENSION
                        )
                    )
                )
            )
        )

        private val EXPECTED_POST = ComicsRepository.Comics(
            id = AN_ID,
            content = A_MARVEL_TITLE,
            thumbnail = "$PATH.$EXTENSION"
        )
    }
}
