package com.permana.dikshatechnicalassessment.core.data.authentication.implementation.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.permana.dikshatechnicalassessment.core.data.DikshaResponse
import com.permana.dikshatechnicalassessment.core.data.authentication.api.model.Login
import com.permana.dikshatechnicalassessment.core.data.authentication.api.repository.AuthenticationRepository
import com.permana.dikshatechnicalassessment.core.data.authentication.implementation.mapper.toLogin
import com.permana.dikshatechnicalassessment.core.data.authentication.implementation.remote.AuthenticationApi
import com.permana.dikshatechnicalassessment.core.data.authentication.implementation.requrest.LoginRequest
import com.permana.dikshatechnicalassessment.core.stash.api.Stash
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.lang.reflect.Type

class AuthenticationRepositoryImpl(val authApi: AuthenticationApi, val stash: Stash): AuthenticationRepository {

    companion object {
        const val KEY_USER_DATA = "KEY_USER_DATA"
    }

    override suspend fun login(loginRequest: LoginRequest): Flow<DikshaResponse<Login>> = flow {
        emit(DikshaResponse.Loading)
        try {
            val response = authApi.login(loginRequest)
            when {
//                response.success == false -> {
//                    emit(DikshaResponse.Error(message = response.statusMessage.orEmpty()))
//                }

                response.token.isNullOrEmpty()  -> {
                    emit(DikshaResponse.Error(message = "Server Error"))
                }

                else -> {
                    emit(DikshaResponse.Success(response.toLogin()))
                }
            }
        } catch (e : Exception){
            // Cuz Response if username/password wrong return string only,then i default message
            emit(DikshaResponse.Error("Username or Password wrong"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun saveUser(login: Login): Boolean = stash.save(KEY_USER_DATA, Gson().toJson(login))

    override suspend fun readUserData(): Login {
        return withContext(Dispatchers.IO) {
            val json = stash.read(KEY_USER_DATA, "")
            if (json.isEmpty()) return@withContext Login()
            Gson().fromJson(json, Login::class.java)
        }
    }

    override suspend fun clearUserData(): Boolean = stash.remove(KEY_USER_DATA)


}