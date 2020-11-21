package com.example.marchenandroid.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marchenandroid.data.SessionManager
import com.example.marchenandroid.data.domain.User

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val _fullname = MutableLiveData<String>()
    val fullname: LiveData<String> = _fullname

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _userRole = MutableLiveData<Int>()
    val userRole: LiveData<Int> = _userRole

    private var sessionManager: SessionManager

    init {
        sessionManager = SessionManager(getApplication())
        _user.value = sessionManager.fetchUserData()
        _userRole.value = sessionManager.fetchUserRole()!!
    }

    fun getId(): Int {
        return _userRole.value!!
    }

    fun quit() {
        sessionManager.removeAllData()
    }
}