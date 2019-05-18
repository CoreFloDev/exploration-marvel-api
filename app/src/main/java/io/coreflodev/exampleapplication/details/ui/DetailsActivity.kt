package io.coreflodev.exampleapplication.details.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import io.coreflodev.exampleapplication.R
import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.arch.ScreenView
import io.coreflodev.exampleapplication.details.DetailsInput
import io.coreflodev.exampleapplication.details.DetailsOutput
import io.coreflodev.exampleapplication.details.injection.DetailsStateHolder
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

class DetailsActivity : AppCompatActivity(), ScreenView<DetailsInput, DetailsOutput> {

    override fun inputs(): Observable<DetailsInput> = Observable.empty()

    override fun render(output: DetailsOutput) {
        when (output) {
            is DetailsOutput.Display -> {
                post_title.text = output.data.postTitle
                post_body.text = output.data.postBody
                user_name.text = output.data.userName
                number_of_comments.text = output.data.numberOfComments
            }
            DetailsOutput.Loading -> TODO()
            DetailsOutput.Error -> TODO()
        }
    }

    @Inject
    lateinit var screen: Screen<DetailsInput, DetailsOutput>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        ViewModelProviders.of(this, DetailsStateHolder.Factory(application, intent.getStringExtra(POST_ID)))
            .get(DetailsStateHolder::class.java)
            .detailsComponent
            .inject(this)

        screen.attach(this)
    }

    override fun onDestroy() {
        screen.detach()
        super.onDestroy()
    }

    companion object {
        private const val POST_ID = "post_id"

        fun start(postId: String, activity: Activity) {
            val intent = Intent(activity, DetailsActivity::class.java).apply {
                putExtra(POST_ID, postId)
            }
            activity.startActivity(intent)
        }
    }
}
