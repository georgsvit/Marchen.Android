package com.example.marchenandroid.data.network.dto.responses

import com.google.gson.annotations.SerializedName
import java.util.*

data class ChildResponse(
    @SerializedName("id")
    var Id: Int,
    @SerializedName("firstName")
    var Firstname: String,
    @SerializedName("lastName")
    var Lastname: String,
    @SerializedName("avatarURL")
    var AvatarURL: String,
    @SerializedName("teacherId")
    var TeacherId: Int,
    @SerializedName("dateOfBirth")
    var DateOfBirth: Date
)