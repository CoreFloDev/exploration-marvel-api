package io.coreflodev.exampleapplication.core

import dagger.Module
import io.coreflodev.exampleapplication.common.injection.ApplicationModule

@Module
class ApplicationModuleTest: ApplicationModule() {

    override fun provideServerUrl() = "http://${HttpServerRule.MOCK_SERVER_HOST}:${HttpServerRule.MOCK_SERVER_PORT}"
}
