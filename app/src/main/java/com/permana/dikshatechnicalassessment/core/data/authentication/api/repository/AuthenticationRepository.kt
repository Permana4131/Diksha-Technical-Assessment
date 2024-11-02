package com.permana.dikshatechnicalassessment.core.data.authentication.api.repository

import com.permana.dikshatechnicalassessment.core.data.DikshaResponse
import com.permana.dikshatechnicalassessment.core.data.authentication.api.model.Login
import com.permana.dikshatechnicalassessment.core.data.authentication.implementation.requrest.LoginRequest
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    /** post login user to api **/
    suspend fun login(loginRequest: LoginRequest): Flow<DikshaResponse<Login>>

    /** save user data to stash **/
    suspend fun saveUser(login: Login): Boolean

    /** get user data to stash **/
    suspend fun readUserData(): Login

    /** clear user data to stash **/
    suspend fun clearUserData(): Boolean

}