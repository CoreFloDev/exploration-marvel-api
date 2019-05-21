package io.coreflodev.exampleapplication.core

import io.coreflodev.exampleapplication.common.ExampleApplication
import io.coreflodev.exampleapplication.common.injection.ApplicationComponent
import io.coreflodev.exampleapplication.common.injection.DaggerApplicationComponent

class ApplicationTest : ExampleApplication() {

    override val applicationComponent: ApplicationComponent
        get() = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModuleTest())
            .build()
}
