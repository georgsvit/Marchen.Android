package com.example.marchenandroid.ui.child_form

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marchenandroid.R
import com.example.marchenandroid.data.SessionManager
import com.example.marchenandroid.data.domain.Child
import com.example.marchenandroid.data.network.ApiClient
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.data.network.dto.requests.ChildRegisterRequest
import kotlinx.coroutines.launch

class ChildFormViewModel(application: Application) : AndroidViewModel(application) {
    private val _childId = MutableLiveData<Int>()
    val childId: LiveData<Int> = _childId

    private val _child = MutableLiveData<Child>()
    val child: LiveData<Child> = _child

    private val _registerForm = MutableLiveData<ChildFormState>()
    val registerFormState: LiveData<ChildFormState> = _registerForm

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager
    private lateinit var _token: String

    init {
        apiClient = ApiClient()
        sessionManager = SessionManager(getApplication())
        _token = sessionManager.fetchAuthToken()!!

        _childId.value = sessionManager.fetchChildId()!!
        sessionManager.removeChildId()

        if (_childId.value != 0) {
            getChild(_childId.value!!)
        }
    }

    fun registerDataChanged(name: String, surname: String) {
        when {
            name == "" -> {
                _registerForm.value = ChildFormState(nameError = R.string.invalid_name)
            }
            surname == "" -> {
                _registerForm.value = ChildFormState(surnameError = R.string.invalid_surname)
            }
            else -> {
                _registerForm.value = ChildFormState(isDataValid = true)
            }
        }
    }

    fun onSaveClick(name: String, surname: String) {
        if (_childId.value == 0) {
            val request = ChildRegisterRequest(name, surname)
            registerChild(request)
        } else {
            //TODO: Save edited data
        }
    }

    private fun registerChild(request: ChildRegisterRequest) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val response = apiClient.getApiService().childRegister("Bearer $_token", request)
                Log.i("API", "Procedure: Child Register Value: ${response}")
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.i("API", "Procedure: Child Register Error: ${e}")
                _status.value = ApiStatus.ERROR
            }
        }
    }

    private fun updateChild() {
        // TODO: Update child data
    }

    private fun getChild(childId: Int) {
        viewModelScope.launch {
            // TODO: Get child data
        }
    }

}