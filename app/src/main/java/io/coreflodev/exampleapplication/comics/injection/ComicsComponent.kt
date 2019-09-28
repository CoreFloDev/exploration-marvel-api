package io.coreflodev.exampleapplication.comics.injection

import dagger.Component
import io.coreflodev.exampleapplication.common.arch.Screen
import io.coreflodev.exampleapplication.common.injection.ApplicationComponent
import io.coreflodev.exampleapplication.comics.arch.ComicsInput
import io.coreflodev.exampleapplication.comics.arch.ComicsOutput

@ComicsScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ComicsModule::class]
)
interface ComicsComponent {

    fun screen(): Screen<ComicsInput, ComicsOutput>
}
