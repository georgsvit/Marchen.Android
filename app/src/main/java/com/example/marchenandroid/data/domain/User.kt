package com.example.marchenandroid.data.domain

data class User(
    val Id: Int,
    val Email: String,
    val Password: String,
    val Fullname: String,
    val RoleId: Int,
    val Children: List<Child>?
) {
    fun getRole(): String {
        return when (RoleId) {
            1 -> "Teacher"
            2 -> "Parent"
            else -> "Unknown"
        }
    }
}