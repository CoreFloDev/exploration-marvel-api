package io.coreflodev.exampleapplication.details.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import io.coreflodev.exampleapplication.R
import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.arch.ScreenView
import io.coreflodev.exampleapplication.details.DetailsInput
import io.coreflodev.exampleapplication.details.DetailsOutput
import io.coreflodev.exampleapplication.details.injection.DetailsStateHolder
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

class DetailsActivity : AppCompatActivity(), ScreenView<DetailsInput, DetailsOutput> {

    override fun render(output: DetailsOutput) {
        when (output) {
            is DetailsOutput.Display -> {
                post_title.text = output.data.postTitle
                post_body.text = output.data.postBody
                user_name.text = output.data.userName
                number_of_comments.text = output.data.numberOfComments
                loading_details_activity.visibility = View.GONE
                error_text.visibility = View.GONE
                content_activity_details.visibility = View.VISIBLE
            }
            DetailsOutput.Loading -> {
                error_text.visibility = View.GONE
                content_activity_details.visibility = View.GONE
                loading_details_activity.visibility = View.VISIBLE
            }
            DetailsOutput.Error -> {
                loading_details_activity.visibility = View.GONE
                content_activity_details.visibility = View.GONE
                error_text.visibility = View.VISIBLE
            }
        }
    }

    @Inject
    lateinit var screen: Screen<DetailsInput, DetailsOutput>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
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
