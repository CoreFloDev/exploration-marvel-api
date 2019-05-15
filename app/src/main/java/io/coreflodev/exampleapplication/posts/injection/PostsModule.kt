package io.coreflodev.exampleapplication.posts.injection

import dagger.Binds
import dagger.Module
import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.posts.PostsInput
import io.coreflodev.exampleapplication.posts.PostsOutput
import io.coreflodev.exampleapplication.posts.PostsScreen
import io.coreflodev.exampleapplication.posts.repo.PostsRepository
import io.coreflodev.exampleapplication.posts.repo.TypicodePostsRepository

@Module
abstract class PostsModule {

    @Binds
    abstract fun providePostsScreen(screen: PostsScreen): Screen<PostsInput, PostsOutput>

    @Binds
    abstract fun providePostsRepository(repository: TypicodePostsRepository): PostsRepository
}
