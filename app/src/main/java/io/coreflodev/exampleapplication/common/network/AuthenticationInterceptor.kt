package io.coreflodev.exampleapplication.common.network

import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest

class AuthenticationInterceptor(
    private val apiKey: String,
    private val privateKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val timestamp = (System.currentTimeMillis() / 1000).toString()
        val hash = hashMd5(timestamp + privateKey + apiKey)

        val request = chain.request()
        val url = request.url().newBuilder()
            .addQueryParameter("ts", timestamp)
            .addQueryParameter("apikey", apiKey)
            .addQueryParameter("hash", hash)
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(url)
                .build()
        )
    }

    private fun hashMd5(input: String): String =
        MessageDigest
            .getInstance("MD5")
            .digest(input.toByteArray())
            .joinToString("") {
                String.format("%02x", it)
            }
}
