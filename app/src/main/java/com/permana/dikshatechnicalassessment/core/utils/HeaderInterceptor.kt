package com.permana.xsisassessment.core.utils

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    val apiKey = "token if any"
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
            .header(HEADER_AUTHORIZATION, apiKey)

        val requestBuild = requestBuilder.build()
        return chain.proceed(requestBuild)
    }

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
    }
}