package io.coreflodev.exampleapplication.core

import android.content.Context
import dagger.Module
import io.coreflodev.exampleapplication.common.injection.ApplicationModule

@Module
class ApplicationModuleTest(context: Context): ApplicationModule(context) {

    override fun provideServerUrl() = "http://${HttpServerRule.MOCK_SERVER_HOST}:${HttpServerRule.MOCK_SERVER_PORT}"
}
