package io.coreflodev.exampleapplication.details.injection

import dagger.Component
import io.coreflodev.exampleapplication.common.injection.ApplicationComponent
import io.coreflodev.exampleapplication.details.ui.DetailsActivity

@DetailsScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [DetailsModule::class]
)
interface DetailsComponent {

    fun inject(activity: DetailsActivity)
}