package com.example.marchenandroid.data.network.dto.responses

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
    var DateOfBirth: String
) {
    fun getDOB() : String {
        val ld = LocalDate.parse(DateOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        return "${ld.dayOfMonth}/${ld.monthValue}/${ld.year}"
    }
}