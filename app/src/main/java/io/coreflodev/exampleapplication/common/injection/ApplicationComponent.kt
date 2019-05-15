package io.coreflodev.exampleapplication.common.injection

import dagger.Component
import retrofit2.Retrofit

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun retrofit(): Retrofit

}
