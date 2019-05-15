package io.coreflodev.exampleapplication.list.injection

import dagger.Binds
import dagger.Module
import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.list.ListInput
import io.coreflodev.exampleapplication.list.ListOutput
import io.coreflodev.exampleapplication.list.ListScreen
import io.coreflodev.exampleapplication.list.repo.ListRepository
import io.coreflodev.exampleapplication.list.repo.TypicodeListRepository

@Module
abstract class ListModule {

    @Binds
    abstract fun provideListScreen(screen: ListScreen): Screen<ListInput, ListOutput>

    @Binds
    abstract fun provideListRepository(repository: TypicodeListRepository): ListRepository
}
