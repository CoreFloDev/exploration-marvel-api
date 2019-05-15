package io.coreflodev.exampleapplication.posts.injection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.coreflodev.exampleapplication.common.ExampleApplication

class PostsStateHolder(app: Application) : AndroidViewModel(app) {

    val postsComponent: PostsComponent =
        DaggerPostsComponent.builder()
            .applicationComponent(ExampleApplication.applicationComponent(app))
            .build()
}
