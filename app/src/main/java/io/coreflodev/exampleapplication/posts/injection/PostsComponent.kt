package io.coreflodev.exampleapplication.posts.injection

import dagger.Component
import io.coreflodev.exampleapplication.common.injection.ApplicationComponent
import io.coreflodev.exampleapplication.posts.ui.PostsActivity

@PostsScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [PostsModule::class]
)
interface PostsComponent {

    fun inject(postsActivity: PostsActivity)
}
