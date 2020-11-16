package com.example.marchenandroid.data.network.dto.responses

import com.google.gson.annotations.SerializedName
import java.util.*

data class JWTTokenResponse(
    @SerializedName("accessToken")
    var AccessToken: String,
    @SerializedName("expiresAt")
    var ExpiresAt: Date,
    @SerializedName("fullname")
    var Fullname: String,
    @SerializedName("roleName")
    var RoleName: String
)