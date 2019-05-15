package io.coreflodev.exampleapplication.details.injection

import androidx.lifecycle.AndroidViewModel
import io.coreflodev.exampleapplication.common.ExampleApplication

class DetailsStateHolder(app: ExampleApplication) : AndroidViewModel(app) {

    val detailsComponent: DetailsComponent =
        DaggerDetailsComponent.builder()
            .applicationComponent(ExampleApplication.applicationComponent(app))
            .build()
}