package io.coreflodev.exampleapplication.comics.injection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.coreflodev.exampleapplication.common.MarvelApplication

class ComicsStateHolder(app: Application) : AndroidViewModel(app) {

    val comicsComponent: ComicsComponent =
        DaggerComicsComponent.builder()
            .applicationComponent(MarvelApplication.applicationComponent(app))
            .build()
}
