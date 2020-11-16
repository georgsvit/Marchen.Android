package com.example.marchenandroid.data.network.dto.responses

import com.google.gson.annotations.SerializedName

data class OptionResponse(
    @SerializedName("id")
    var Id: Int,
    @SerializedName("answerText")
    var AnswerText: String,
    @SerializedName("toUnitId")
    var ToUnitId: Int
)