package com.example.marchenandroid.data.network.dto.responses

import com.google.gson.annotations.SerializedName

data class SavepointResponse(
    @SerializedName("childFirstName")
    var ChildFirstname: String,
    @SerializedName("childLastName")
    var ChildLastname: String,
    @SerializedName("childId")
    var ChildId: Int,
    @SerializedName("unitId")
    var UnitId: Int
)