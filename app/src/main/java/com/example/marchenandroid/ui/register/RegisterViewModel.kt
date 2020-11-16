package com.example.marchenandroid.ui.register

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marchenandroid.R
import com.example.marchenandroid.data.SessionManager
import com.example.marchenandroid.data.network.ApiClient
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.data.network.dto.requests.RegisterRequest
import com.example.marchenandroid.data.network.dto.responses.JWTTokenResponse
import com.example.marchenandroid.util.isEmailValid
import com.example.marchenandroid.util.isPasswordValid
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _jwtTokenResponse = MutableLiveData<JWTTokenResponse>()

    init {
        _jwtTokenResponse.value = null
    }

    fun registerDataChanged(email: String, password: String, fullname: String, role: Int) {
        if (!isEmailValid(email)) {
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else if (fullname.isNullOrBlank()) {
            _registerForm.value = RegisterFormState(fullnameError = R.string.invalid_fullname)
        } else if (role == 0) {
            _registerForm.value = RegisterFormState(roleError = R.string.invalid_role)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    fun onRegisterClick(email: String, password: String, fullname: String, role: Int) {
        if (role != 0) {
            viewModelScope.launch {
                _status.value = ApiStatus.LOADING

                val user = RegisterRequest(Email = email, Password = password, Fullname = fullname, RoleId = role)
                val apiClient = ApiClient()
                val sessionManager = SessionManager(context = getApplication())

                try {
                    _jwtTokenResponse.value = apiClient.getApiService().register(request = user)
                    Log.i("API", "Procedure: Register Token: ${_jwtTokenResponse.value}")
                    if (_jwtTokenResponse.value?.AccessToken != null) {
                        sessionManager.saveAllData(_jwtTokenResponse.value!!)
                        _status.value = ApiStatus.DONE
                        Log.i("API", "Procedure: Register Success: Token saved")
                    }
                } catch (e: Exception) {
                    Log.i("API", "Procedure: Register Error: ${e}")
                    _status.value = ApiStatus.ERROR
                }
            }
        } else {
            registerDataChanged(email, password, fullname, role)
        }
    }
}