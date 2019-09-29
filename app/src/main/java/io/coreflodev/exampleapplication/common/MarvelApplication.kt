package io.coreflodev.exampleapplication.common

import android.app.Application
import android.content.Context
import io.coreflodev.exampleapplication.common.injection.ApplicationComponent
import io.coreflodev.exampleapplication.common.injection.ApplicationModule
import io.coreflodev.exampleapplication.common.injection.DaggerApplicationComponent

open class MarvelApplication : Application() {

    protected open val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(baseContext))
            .build()
    }

    companion object {

        fun applicationComponent(context: Context) =
            (context.applicationContext as MarvelApplication).applicationComponent
    }


}
