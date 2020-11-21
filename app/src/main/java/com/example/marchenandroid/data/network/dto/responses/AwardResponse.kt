package com.example.marchenandroid.data.network.dto.responses

import com.google.gson.annotations.SerializedName

data class AwardResponse(
        @SerializedName("fairytaleName")
        var FairytaleName: String,
        @SerializedName("awardURL")
        var AwardURL: String,
        @SerializedName("dateTime")
        var DateTime: String
)