package io.coreflodev.exampleapplication.common.injection

import android.os.Build
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import io.coreflodev.exampleapplication.BuildConfig
import io.coreflodev.exampleapplication.common.network.AuthenticationInterceptor
import io.coreflodev.exampleapplication.common.network.MarvelApi
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named

@Module
open class ApplicationModule {

    @Provides
    @Named(SERVER_URL)
    open fun provideServerUrl() = "https://gateway.marvel.com"

    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    fun provideRetrofit(moshi: Moshi, @Named(SERVER_URL) serverUrl: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(serverUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

    @Provides
    fun provideMarvelApi(retrofit: Retrofit): MarvelApi =
        retrofit.create(MarvelApi::class.java)

    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(AuthenticationInterceptor(
            BuildConfig.MARVEL_API_KEY,
            BuildConfig.MARVEL_PRIVATE_KEY
        ))
        .build()

    companion object {
        const val SERVER_URL = "SERVER_URL"
    }
}
