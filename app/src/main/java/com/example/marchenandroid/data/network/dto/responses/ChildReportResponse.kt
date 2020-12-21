package com.example.marchenandroid.data.network.dto.responses

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class ChildReportResponse(
    @SerializedName("id")
    var Id: Int,
    @SerializedName("dateTime")
    var DateTime: String,
    @SerializedName("unitFairytaleName")
    var UnitFairytaleName: String
) {
    fun getDate() : String {
        val time = DateTime.subSequence(startIndex = 11, 19)
        val ld = LocalDate.parse(DateTime.subSequence(0, 19), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        return "Created at $time ${ld.dayOfMonth}/${ld.monthValue}/${ld.year}"
    }
}