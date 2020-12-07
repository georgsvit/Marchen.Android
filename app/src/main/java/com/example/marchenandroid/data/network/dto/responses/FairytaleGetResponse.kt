package com.example.marchenandroid.data.network.dto.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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
    var FirstUnitId: Int,
    @SerializedName("pictureURL")
    var PictureURL: String,
    @SerializedName("minAge")
    var MinAge: Int,
    @SerializedName("maxAge")
    var MaxAge: Int,
    @SerializedName("creationDate")
    var CreationDate: String
) : Parcelable {
    fun getDate() : String {
        val ld = LocalDate.parse(CreationDate.subSequence(0, 19), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        return "${ld.dayOfMonth}/${ld.monthValue}/${ld.year}"
    }

    fun getAgeRange(): String {
        return "$MinAge-$MaxAge"
    }
}