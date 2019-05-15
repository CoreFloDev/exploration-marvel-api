package io.coreflodev.exampleapplication.details.injection

import dagger.Module
import dagger.Provides
import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.details.DetailsInput
import io.coreflodev.exampleapplication.details.DetailsOutput
import io.coreflodev.exampleapplication.details.DetailsScreen

@Module
class DetailsModule {

    @Provides
    @DetailsScope
    fun provideDetailsScreen() : Screen<DetailsInput, DetailsOutput> =
            DetailsScreen()
}