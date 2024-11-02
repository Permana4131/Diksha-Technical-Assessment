package com.permana.dikshatechnicalassessment.core.data.authentication.implementation.mapper

import com.permana.dikshatechnicalassessment.core.data.authentication.api.model.Login
import com.permana.dikshatechnicalassessment.core.data.authentication.implementation.response.LoginResponse

fun LoginResponse.toLogin() = Login(
    token = this.token ?: ""
)