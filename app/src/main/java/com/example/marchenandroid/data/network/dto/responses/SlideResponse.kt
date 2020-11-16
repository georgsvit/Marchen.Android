package com.example.marchenandroid.data.network.dto.responses

import com.google.gson.annotations.SerializedName

data class SlideResponse(
    @SerializedName("id")
    var Id: Int,
    @SerializedName("content")
    var Content: String,
    @SerializedName("imagePath")
    var ImagePath: String
)
