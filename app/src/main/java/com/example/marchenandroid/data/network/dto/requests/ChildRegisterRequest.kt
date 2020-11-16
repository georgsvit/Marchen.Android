package com.example.marchenandroid.data.network.dto.requests

import com.google.gson.annotations.SerializedName

data class ChildRegisterRequest(
    @SerializedName("Firstname")
    var Firstname: String,
    @SerializedName("Lastname")
    var Lastname: String
)