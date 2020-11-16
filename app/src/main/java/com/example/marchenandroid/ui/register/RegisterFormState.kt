package com.example.marchenandroid.ui.register

data class RegisterFormState(val emailError: Int? = null,
                             val passwordError: Int? = null,
                             val fullnameError: Int? = null,
                             val roleError: Int? = null,
                             val isDataValid: Boolean = false)