package io.coreflodev.exampleapplication.posts.injection

import dagger.Module
import dagger.Provides
import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.network.TypicodeApi
import io.coreflodev.exampleapplication.posts.PostsInput
import io.coreflodev.exampleapplication.posts.PostsOutput
import io.coreflodev.exampleapplication.posts.PostsScreen
import io.coreflodev.exampleapplication.posts.repo.PostsRepository
import io.coreflodev.exampleapplication.posts.repo.TypicodePostsRepository
import io.coreflodev.exampleapplication.posts.use_cases.LoadListOfPostsUseCase
import io.coreflodev.exampleapplication.posts.use_cases.NavigateToDetailsUseCase

@Module
class PostsModule {

    @Provides
    @PostsScope
    fun provideLoadListOfPostsUseCase(repository: PostsRepository) = LoadListOfPostsUseCase(repository)

    @Provides
    @PostsScope
    fun provideNavigateToDetailsUseCase() = NavigateToDetailsUseCase()

    @Provides
    @PostsScope
    fun providePostsScreen(
        loadListOfPostsUseCase: LoadListOfPostsUseCase,
        navigateToDetailsUseCase: NavigateToDetailsUseCase
    ): Screen<PostsInput, PostsOutput> =
        PostsScreen(loadListOfPostsUseCase, navigateToDetailsUseCase)

    @Provides
    @PostsScope
    fun providePostsRepository(typicodeApi: TypicodeApi): PostsRepository =
        TypicodePostsRepository(typicodeApi)
}
