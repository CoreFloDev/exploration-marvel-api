package io.coreflodev.exampleapplication.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.coreflodev.exampleapplication.R
import io.coreflodev.exampleapplication.common.arch.ScreenView
import io.reactivex.Observable

class ListActivity : AppCompatActivity(), ScreenView<ListInput, ListOutput> {

    override fun inputs(): Observable<ListInput> {
        return Observable.empty()
    }

    override fun render(output: ListOutput) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
