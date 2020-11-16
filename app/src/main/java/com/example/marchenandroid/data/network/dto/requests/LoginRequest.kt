package com.example.marchenandroid.data.network.dto.requests

import com.google.gson.annotations.SerializedName

class LoginRequest(
    @SerializedName("Email")
    var Email: String,
    @SerializedName("Password")
    var Password: String
)