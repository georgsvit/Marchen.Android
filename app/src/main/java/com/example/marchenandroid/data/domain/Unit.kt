package com.example.marchenandroid.data.domain

data class Unit(
    val Id: Int,
    val QuestionText: String,
    val FairytaleId: Int?,
    val Slides: List<Slide>,
    val Options: List<Option>
)