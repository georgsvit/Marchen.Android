package com.example.marchenandroid.util

import android.util.Patterns

fun isPasswordValid(password: String): Boolean {
    return password.length > 5
}

fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

