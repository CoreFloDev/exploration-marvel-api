package io.coreflodev.exampleapplication.details.injection

import dagger.Module
import dagger.Provides
import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.network.TypicodeApi
import io.coreflodev.exampleapplication.details.DetailsInput
import io.coreflodev.exampleapplication.details.DetailsOutput
import io.coreflodev.exampleapplication.details.DetailsScreen
import io.coreflodev.exampleapplication.details.repo.DetailsRepository
import io.coreflodev.exampleapplication.details.repo.TypicodeDetailsRepository
import io.coreflodev.exampleapplication.details.use_cases.DisplayPostDetailsUseCase

@Module
class DetailsModule(private val postId: String) {

    @Provides
    @DetailsScope
    fun providePostDetailsUseCase(repository: DetailsRepository) =
        DisplayPostDetailsUseCase(repository)

    @Provides
    @DetailsScope
    fun provideDetailsScreen(displayPostDetailsUseCase: DisplayPostDetailsUseCase) : Screen<DetailsInput, DetailsOutput> =
            DetailsScreen(postId, displayPostDetailsUseCase)

    @Provides
    @DetailsScope
    fun provideDetailsRepository(typicodeApi: TypicodeApi) : DetailsRepository =
            TypicodeDetailsRepository(typicodeApi)
}
