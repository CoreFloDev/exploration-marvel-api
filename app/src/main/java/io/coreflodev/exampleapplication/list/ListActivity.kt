package io.coreflodev.exampleapplication.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import io.coreflodev.exampleapplication.R
import io.coreflodev.exampleapplication.common.arch.ScreenView
import io.coreflodev.exampleapplication.list.injection.ListStateHolder
import io.reactivex.Observable
import javax.inject.Inject

class ListActivity : AppCompatActivity(), ScreenView<ListInput, ListOutput> {

    @Inject
    lateinit var screen: ScreenView<ListInput, ListOutput>

    override fun inputs(): Observable<ListInput> = Observable.empty()

    override fun render(output: ListOutput) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewModelProviders.of(this)[ListStateHolder::class.java]
            .listComponent
            .inject(this)
    }

}
