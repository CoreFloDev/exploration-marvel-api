package io.coreflodev.exampleapplication.details.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.coreflodev.exampleapplication.R
import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.arch.ScreenView
import io.coreflodev.exampleapplication.details.DetailsInput
import io.coreflodev.exampleapplication.details.DetailsOutput
import io.coreflodev.exampleapplication.details.injection.DetailsStateHolder
import io.reactivex.Observable
import javax.inject.Inject

class DetailsActivity : AppCompatActivity(), ScreenView<DetailsInput, DetailsOutput> {

    override fun inputs(): Observable<DetailsInput> = Observable.empty()

    override fun render(output: DetailsOutput) {
        when (output) {
            is DetailsOutput.Display -> {
                
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

        ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(DetailsStateHolder::class.java)
            .detailsComponent
            .inject(this)

        screen.attach(this)
    }

    override fun onDestroy() {
        screen.detach()
        super.onDestroy()
    }

}