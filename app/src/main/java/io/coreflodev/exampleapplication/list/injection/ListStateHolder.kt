package io.coreflodev.exampleapplication.list.injection

import androidx.lifecycle.AndroidViewModel
import io.coreflodev.exampleapplication.ExampleApplication

class ListStateHolder(app: ExampleApplication) : AndroidViewModel(app) {

    val listComponent =
            DaggerListComponent.builder()
                .applicationComponent(ExampleApplication.applicationComponent(app))
                .build()
}
