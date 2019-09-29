package io.coreflodev.exampleapplication.comics

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.view.clicks
import io.coreflodev.exampleapplication.R
import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.arch.ScreenView
import io.coreflodev.exampleapplication.comics.arch.ComicsInput
import io.coreflodev.exampleapplication.comics.arch.ComicsOutput
import io.coreflodev.exampleapplication.comics.injection.ComicsStateHolder
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_comics.*

open class ComicsActivity : AppCompatActivity(), ScreenView<ComicsInput, ComicsOutput> {

    private lateinit var screen: Screen<ComicsInput, ComicsOutput>

    private val adapter = ComicsAdapter()

    override fun inputs(): Observable<ComicsInput> = Observable.mergeArray(
        adapter.onItemClicked().map { ComicsInput.ItemClicked(it) },
        error_button.clicks().map { ComicsInput.Retry }
    )

    override fun render(output: ComicsOutput) {
        when (output) {
            is ComicsOutput.Display -> {
                adapter.update(output.data)
                loading_comics_activity.visibility = View.GONE
                error_comics_activity.visibility = View.GONE
                recycler_view_comics_activity.visibility = View.VISIBLE
            }
            ComicsOutput.Loading -> {
                error_comics_activity.visibility = View.GONE
                recycler_view_comics_activity.visibility = View.GONE
                loading_comics_activity.visibility = View.VISIBLE
            }
            ComicsOutput.Error -> {
                loading_comics_activity.visibility = View.GONE
                recycler_view_comics_activity.visibility = View.GONE
                error_comics_activity.visibility = View.VISIBLE
            }
            is ComicsOutput.ToDetail -> println("move to the next activity (TODO)")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comics)

        screen = ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(ComicsStateHolder::class.java)
            .comicsComponent
            .screen()

        screen.attach(this)

        recycler_view_comics_activity.layoutManager = GridLayoutManager(this, 2)
        recycler_view_comics_activity.adapter = adapter
    }

    override fun onDestroy() {
        screen.detach()
        super.onDestroy()
    }

}
