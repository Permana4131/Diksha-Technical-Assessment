package com.permana.dikshatechnicalassessment.core.data.authentication.implementation.requrest

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    var username: String = "",
    @SerializedName("password")
    var password: String = "",
)
