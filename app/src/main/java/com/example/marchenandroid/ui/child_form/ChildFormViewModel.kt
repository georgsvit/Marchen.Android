package com.example.marchenandroid.ui.child_form

import android.app.Application
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marchenandroid.R
import com.example.marchenandroid.data.SessionManager
import com.example.marchenandroid.data.network.ApiClient
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.data.network.dto.requests.ChildRequest
import com.example.marchenandroid.data.network.dto.responses.AvatarResponse
import com.example.marchenandroid.data.network.dto.responses.ChildResponse
import kotlinx.coroutines.launch

class ChildFormViewModel(application: Application) : AndroidViewModel(application) {
    private val _childId = MutableLiveData<Int>()
    val childId: LiveData<Int> = _childId

    private val _child = MutableLiveData<ChildResponse>()
    val child: LiveData<ChildResponse> = _child

    private val _formState = MutableLiveData<ChildFormState>()
    val formState: LiveData<ChildFormState> = _formState

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _getStatus = MutableLiveData<ApiStatus>()
    val getStatus: LiveData<ApiStatus> = _getStatus

    private val _avatars = MutableLiveData<List<AvatarResponse>>()
    val avatars: LiveData<List<AvatarResponse>> = _avatars

    private val _selectedAvatar = MutableLiveData<AvatarResponse>()
    val selectedAvatar: LiveData<AvatarResponse> = _selectedAvatar


    private var apiClient: ApiClient
    private var sessionManager: SessionManager
    private var _token: String

    init {
        apiClient = ApiClient()
        sessionManager = SessionManager(getApplication())
        _token = sessionManager.fetchAuthToken()!!

        _childId.value = sessionManager.fetchChildId()!!
        sessionManager.removeChildId()

        if (_childId.value != 0) {
            getChild(_childId.value!!)
        }
        _formState.value = ChildFormState(isDataValid = true)

        getAvatars()

        _selectedAvatar.value = null
    }

    fun dataChanged(name: String, surname: String, teacherId: String) {
        when {
            name == "" -> {
                _formState.value = ChildFormState(nameError = R.string.invalid_name)
            }
            surname == "" -> {
                _formState.value = ChildFormState(surnameError = R.string.invalid_surname)
            }
            !teacherId.isDigitsOnly() -> {
                _formState.value = ChildFormState(teacherError = R.string.invalid_teacher)
            }
            _selectedAvatar.value == null -> {
                _formState.value = ChildFormState(avatarError = R.string.invalid_avatar)
            }
            else -> {
                _formState.value = ChildFormState(isDataValid = true)
            }
        }
    }

    fun onSaveClick(name: String, surname: String, teacherId: Int) {
        val request = ChildRequest(name, surname, _selectedAvatar.value!!.Id, teacherId)
        if (_childId.value == 0) {
            registerChild(request)
        } else {
            if (name != _child.value!!.Firstname || surname != _child.value!!.Lastname || teacherId != _child.value!!.TeacherId) {
                updateChild(request)
            }
        }
    }

    private fun getAvatars() {
        viewModelScope.launch {
            //_status.value = ApiStatus.LOADING
            try {
                _avatars.value = apiClient.getApiService().getAvatars("Bearer $_token")

                if (_child.value != null) {
                    var lst = _avatars.value!!

                    for (el in lst) {
                        if (el.AvatarURL == _child.value!!.AvatarURL) {
                            _selectedAvatar.value = el
                            el.isSelected = true
                        } else {
                            el.isSelected = false
                        }
                    }

                    _avatars.value = lst
                }
                Log.i("API", "Procedure: Get Avatars Value: ${avatars}")
                //_status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.i("API", "Procedure: Get Avatars Error: ${e}")
                //_status.value = ApiStatus.ERROR
            }
        }
    }

    private fun registerChild(request: ChildRequest) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val response = apiClient.getApiService().childRegister("Bearer $_token", request)
                Log.i("API", "Procedure: Child Register Value: ${response}  ${request.TeacherId}")
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.i("API", "Procedure: Child Register Error: ${e}")
                _status.value = ApiStatus.ERROR
            }
        }
    }

    private fun updateChild(request: ChildRequest) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val response = apiClient.getApiService().childEdit(_childId.value!!,"Bearer $_token", request)
                Log.i("API", "Procedure: Child Edit Value: ${response}")
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.i("API", "Procedure: Child Edit Error: ${e}")
                _status.value = ApiStatus.ERROR
            }
        }
    }

    private fun getChild(childId: Int) {
        viewModelScope.launch {
            _getStatus.value = ApiStatus.LOADING
            val apiClient = ApiClient()

            try {
                _child.value = apiClient.getApiService().getChildById(childId, "Bearer $_token")
                _getStatus.value = ApiStatus.DONE
                Log.i("API", "Procedure: GET Child Value: ${_child.value}")
            } catch (e: java.lang.Exception) {
                Log.i("API", "Procedure: GET Child Error: $e")
                _getStatus.value = ApiStatus.ERROR
                _child.value = ChildResponse(-1, "undefined", "undefined", "undefined",-1)
            }
        }
    }

    fun setAvatar(avatar: AvatarResponse) {
        _selectedAvatar.value = avatar

        var lst = _avatars.value!!

        for (el in lst) {
            el.isSelected = el.Id == avatar.Id
        }

        _avatars.value = lst
    }
}