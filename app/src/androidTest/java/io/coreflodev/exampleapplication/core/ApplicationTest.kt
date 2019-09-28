package io.coreflodev.exampleapplication.core

import io.coreflodev.exampleapplication.common.MarvelApplication
import io.coreflodev.exampleapplication.common.injection.ApplicationComponent
import io.coreflodev.exampleapplication.common.injection.DaggerApplicationComponent

class ApplicationTest : MarvelApplication() {

    override val applicationComponent: ApplicationComponent
        get() = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModuleTest())
            .build()
}
