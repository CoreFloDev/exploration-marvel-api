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

@Module
class DetailsModule {

    @Provides
    @DetailsScope
    fun provideDetailsScreen() : Screen<DetailsInput, DetailsOutput> =
            DetailsScreen()

    @Provides
    @DetailsScope
    fun provideDetailsRepository(typicodeApi: TypicodeApi) : DetailsRepository =
            TypicodeDetailsRepository(typicodeApi)
}
