package io.coreflodev.exampleapplication.list.injection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.coreflodev.exampleapplication.ExampleApplication

class ListStateHolder(app: Application) : AndroidViewModel(app) {

    val listComponent: ListComponent =
        DaggerListComponent.builder()
            .applicationComponent(ExampleApplication.applicationComponent(app))
            .build()
}
