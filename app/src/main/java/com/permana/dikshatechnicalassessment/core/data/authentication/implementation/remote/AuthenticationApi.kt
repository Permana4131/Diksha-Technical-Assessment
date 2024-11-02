package com.permana.dikshatechnicalassessment.core.data.authentication.implementation.remote

import com.permana.dikshatechnicalassessment.core.data.authentication.implementation.requrest.LoginRequest
import com.permana.dikshatechnicalassessment.core.data.authentication.implementation.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): LoginResponse
}