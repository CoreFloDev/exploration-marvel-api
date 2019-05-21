package io.coreflodev.exampleapplication.posts.injection

import dagger.Component
import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.injection.ApplicationComponent
import io.coreflodev.exampleapplication.posts.PostsInput
import io.coreflodev.exampleapplication.posts.PostsOutput

@PostsScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [PostsModule::class]
)
interface PostsComponent {

    fun screen(): Screen<PostsInput, PostsOutput>
}
