package com.example.marchenandroid.ui.child_form

data class ChildFormState(val nameError: Int? = null,
                          val surnameError: Int? = null,
                          val teacherError: Int? = null,
                          val avatarError: Int? = null,
                          val isDataValid: Boolean = false)