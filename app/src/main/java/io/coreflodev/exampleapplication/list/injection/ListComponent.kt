package io.coreflodev.exampleapplication.list.injection

import dagger.Component
import io.coreflodev.exampleapplication.common.injection.ApplicationComponent
import io.coreflodev.exampleapplication.list.ui.ListActivity

@ListScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ListModule::class]
)
interface ListComponent {

    fun inject(listActivity: ListActivity)
}
