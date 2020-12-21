package com.example.marchenandroid.data.network.dto.responses

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class AwardResponse(
        @SerializedName("fairytaleName")
        var FairytaleName: String,
        @SerializedName("awardURL")
        var AwardURL: String,
        @SerializedName("dateTime")
        var DateTime: String
) {
        fun getDate() : String {
                val time = DateTime.subSequence(startIndex = 11, 19)
                val ld = LocalDate.parse(DateTime.subSequence(0, 19), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                return "Achieved at $time ${ld.dayOfMonth}/${ld.monthValue}/${ld.year}"
        }
}