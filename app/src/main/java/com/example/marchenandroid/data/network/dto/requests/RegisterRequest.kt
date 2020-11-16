package com.example.marchenandroid.data.network.dto.requests

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("Email")
    var Email: String,
    @SerializedName("Password")
    var Password: String,
    @SerializedName("Fullname")
    var Fullname: String,
    @SerializedName("RoleId")
    var RoleId: Int
)