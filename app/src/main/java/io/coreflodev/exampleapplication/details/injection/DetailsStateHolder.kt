package io.coreflodev.exampleapplication.details.injection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.coreflodev.exampleapplication.common.ExampleApplication

class DetailsStateHolder(app: Application, postId: String) : AndroidViewModel(app) {

    val detailsComponent: DetailsComponent =
        DaggerDetailsComponent.builder()
            .applicationComponent(ExampleApplication.applicationComponent(app))
            .detailsModule(DetailsModule(postId))
            .build()

    class Factory(private val app: Application, private val postId: String) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailsStateHolder(app, postId) as T
        }
    }
}