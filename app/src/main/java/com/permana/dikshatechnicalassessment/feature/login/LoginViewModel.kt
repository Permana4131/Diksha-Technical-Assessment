package com.permana.dikshatechnicalassessment.feature.login

import androidx.lifecycle.ViewModel
import com.permana.dikshatechnicalassessment.core.data.DikshaResponse
import com.permana.dikshatechnicalassessment.core.data.authentication.api.model.Login
import com.permana.dikshatechnicalassessment.core.data.authentication.api.repository.AuthenticationRepository
import com.permana.dikshatechnicalassessment.core.data.authentication.implementation.requrest.LoginRequest
import com.permana.xsisassessment.core.utils.extenstion.getLaunch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll

class LoginViewModel(val authenticationRepository: AuthenticationRepository): ViewModel() {
    private val _login = MutableSharedFlow<DikshaResponse<Login>>()
    val login = _login.asSharedFlow()

    var loginRequest = LoginRequest()

    fun login() {
        getLaunch {
            _login.emitAll(authenticationRepository.login(loginRequest))
        }
    }

    suspend fun saveUserData(token: String): Boolean = authenticationRepository.saveUser(Login(username = loginRequest.username, token =  token))

    suspend fun readUserData(): Login = authenticationRepository.readUserData()

    suspend fun clearUserData(): Boolean = authenticationRepository.clearUserData()
}