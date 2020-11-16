package com.example.marchenandroid.data.network.dto.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FairytaleGetResponse(
    @SerializedName("id")
    var Id: Int,
    @SerializedName("name")
    var Name: String,
    @SerializedName("contents")
    var Contents: String,
    @SerializedName("psychoType")
    var PsychoType: String,
    @SerializedName("firstUnitId")
    var FirstUnitId: Int
) : Parcelable