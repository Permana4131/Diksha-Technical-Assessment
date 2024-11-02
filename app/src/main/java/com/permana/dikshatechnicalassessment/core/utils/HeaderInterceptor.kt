package com.permana.xsisassessment.core.utils

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    val apiKey = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmYzRiMWNhNzNlZGQ4MDc2YWJiY2Y4ODFhMGJmZDU3ZiIsIm5iZiI6MTczMDM3MDkyMy44MTM5ODQ0LCJzdWIiOiI2MGIwODlkNTUyZGM3ZjAwNTc5MzVmYzciLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.fIf_hC5C8NT7bDoJg9j5tcfMGVPSTK33HV7bhiY-qoI"
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