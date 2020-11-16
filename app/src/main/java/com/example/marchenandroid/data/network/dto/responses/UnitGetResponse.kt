package com.example.marchenandroid.data.network.dto.responses

import com.google.gson.annotations.SerializedName

data class UnitGetResponse(
    @SerializedName("id")
    var Id: Int,
    @SerializedName("questionText")
    var QuestionText: String,
    @SerializedName("slides")
    var Slides: List<SlideResponse>,
    @SerializedName("options")
    var Options: List<OptionResponse>
)