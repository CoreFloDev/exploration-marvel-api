package io.coreflodev.exampleapplication.common.injection

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class ApplicationModule {

    @Provides
    fun provideRetrofit() =
        Retrofit.Builder()
            .baseUrl("http://jsonplaceholder.typicode.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
}
