package io.coreflodev.exampleapplication.comics.injection

import dagger.Module
import dagger.Provides
import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.network.MarvelApi
import io.coreflodev.exampleapplication.comics.arch.ComicsInput
import io.coreflodev.exampleapplication.comics.arch.ComicsOutput
import io.coreflodev.exampleapplication.comics.arch.ComicsScreen
import io.coreflodev.exampleapplication.comics.repo.ComicsRepository
import io.coreflodev.exampleapplication.comics.repo.MarvelComicsRepository
import io.coreflodev.exampleapplication.comics.use_cases.LoadListOfComicsUseCase
import io.coreflodev.exampleapplication.comics.use_cases.NavigateToDetailsUseCase

@Module
class ComicsModule {

    @Provides
    @ComicsScope
    fun provideLoadListOfPostsUseCase(repository: ComicsRepository) = LoadListOfComicsUseCase(repository)

    @Provides
    @ComicsScope
    fun provideNavigateToDetailsUseCase() = NavigateToDetailsUseCase()

    @Provides
    @ComicsScope
    fun providePostsScreen(
        loadListOfComicsUseCase: LoadListOfComicsUseCase,
        navigateToDetailsUseCase: NavigateToDetailsUseCase
    ): Screen<ComicsInput, ComicsOutput> =
        ComicsScreen(
            loadListOfComicsUseCase,
            navigateToDetailsUseCase
        )

    @Provides
    @ComicsScope
    fun providePostsRepository(marvelApi: MarvelApi): ComicsRepository =
        MarvelComicsRepository(marvelApi)
}
