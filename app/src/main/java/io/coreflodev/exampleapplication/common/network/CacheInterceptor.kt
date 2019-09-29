package io.coreflodev.exampleapplication.common.network

import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val offlineRequest = chain.request().newBuilder()
            .header(
                "Cache-Control",
                "public, only-if-cached,max-stale=$TEN_MINUTES"
            )
            .build()
        val response = chain.proceed(offlineRequest)
        return if (response.isSuccessful) {
            response
        } else {
            chain.proceed(chain.request())
        }
    }

    companion object {
        private const val TEN_MINUTES = 60 * 10
    }
}
