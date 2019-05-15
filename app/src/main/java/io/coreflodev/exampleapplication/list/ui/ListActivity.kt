package io.coreflodev.exampleapplication.list.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.coreflodev.exampleapplication.R
import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.arch.ScreenView
import io.coreflodev.exampleapplication.list.ListInput
import io.coreflodev.exampleapplication.list.ListOutput
import io.coreflodev.exampleapplication.list.injection.ListStateHolder
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_list.*
import javax.inject.Inject

class ListActivity : AppCompatActivity(), ScreenView<ListInput, ListOutput> {

    @Inject
    lateinit var screen: Screen<ListInput, ListOutput>

    private val adapter = ListAdapter()

    override fun inputs(): Observable<ListInput> = adapter.onItemClicked().map { ListInput.ItemClicked(it) }

    override fun render(output: ListOutput) {
        when (output) {
            is ListOutput.Display -> adapter.update(output.data)
            ListOutput.Loading -> println("loading state")
            ListOutput.Error -> println("error state")
            is ListOutput.ToDetail -> println("moving to ${output.id}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(ListStateHolder::class.java)
            .listComponent
            .inject(this)

        screen.attach(this)

        recycler_view_list_activity.layoutManager = LinearLayoutManager(this)
        recycler_view_list_activity.adapter = adapter
    }

    override fun onDestroy() {
        screen.detach()
        super.onDestroy()
    }

}
