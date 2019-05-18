package io.coreflodev.exampleapplication.posts.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.view.clicks
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

    override fun inputs(): Observable<PostsInput> = Observable.mergeArray(
        adapter.onItemClicked().map { PostsInput.ItemClicked(it) },
        error_button.clicks().map { PostsInput.Retry }
    )

    override fun render(output: PostsOutput) {
        when (output) {
            is PostsOutput.Display -> {
                adapter.update(output.data)
                loading_posts_activity.visibility = View.GONE
                error_posts_activity.visibility = View.GONE
                recycler_view_posts_activity.visibility = View.VISIBLE
            }
            PostsOutput.Loading -> {
                error_posts_activity.visibility = View.GONE
                recycler_view_posts_activity.visibility = View.GONE
                loading_posts_activity.visibility = View.VISIBLE
            }
            PostsOutput.Error -> {
                loading_posts_activity.visibility = View.GONE
                recycler_view_posts_activity.visibility = View.GONE
                error_posts_activity.visibility = View.VISIBLE
            }
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
