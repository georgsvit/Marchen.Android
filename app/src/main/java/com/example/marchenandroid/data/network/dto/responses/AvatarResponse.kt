package com.example.marchenandroid.data.network.dto.responses

import com.google.gson.annotations.SerializedName

data class AvatarResponse(
        @SerializedName("id")
        var Id: Int,
        @SerializedName("avatarURL")
        var AvatarURL: String
)