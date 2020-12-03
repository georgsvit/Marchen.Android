package com.example.marchenandroid.data.network.dto.requests

import com.google.gson.annotations.SerializedName

data class ChildRequest(
    @SerializedName("Firstname")
    var Firstname: String,
    @SerializedName("Lastname")
    var Lastname: String,
    @SerializedName("AvatarId")
    var AvatarId: Int,
    @SerializedName("teacherId")
    var TeacherId: Int,
)