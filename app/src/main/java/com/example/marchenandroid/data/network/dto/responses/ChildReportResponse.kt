package com.example.marchenandroid.data.network.dto.responses

import com.google.gson.annotations.SerializedName

data class ChildReportResponse(
    @SerializedName("id")
    var Id: Int,
    @SerializedName("dateTime")
    var DateTime: String,
    @SerializedName("unitFairytaleName")
    var UnitFairytaleName: String
)