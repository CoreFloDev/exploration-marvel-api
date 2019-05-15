package io.coreflodev.exampleapplication.common.injection

import dagger.Component
import io.coreflodev.exampleapplication.common.network.TypicodeApi

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun typicodeApi(): TypicodeApi

}
