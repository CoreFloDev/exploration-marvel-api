package io.coreflodev.exampleapplication.posts.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.coreflodev.exampleapplication.R
import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.arch.ScreenView
import io.coreflodev.exampleapplication.details.ui.DetailsActivity
import io.coreflodev.exampleapplication.posts.PostsInput
import io.coreflodev.exampleapplication.posts.PostsOutput
import io.coreflodev.exampleapplication.posts.injection.PostsStateHolder
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_posts.*
import javax.inject.Inject

class PostsActivity : AppCompatActivity(), ScreenView<PostsInput, PostsOutput> {

    @Inject
    lateinit var screen: Screen<PostsInput, PostsOutput>

    private val adapter = PostsAdapter()

    override fun inputs(): Observable<PostsInput> = adapter.onItemClicked().map { PostsInput.ItemClicked(it) }

    override fun render(output: PostsOutput) {
        when (output) {
            is PostsOutput.Display -> adapter.update(output.data)
            PostsOutput.Loading -> println("loading state")
            PostsOutput.Error -> println("error state")
            is PostsOutput.ToDetail -> DetailsActivity.start(output.id, this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(PostsStateHolder::class.java)
            .postsComponent
            .inject(this)

        screen.attach(this)

        recycler_view_posts_activity.layoutManager = LinearLayoutManager(this)
        recycler_view_posts_activity.adapter = adapter
    }

    override fun onDestroy() {
        screen.detach()
        super.onDestroy()
    }

}
