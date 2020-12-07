package com.example.marchenandroid.ui.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marchenandroid.data.SessionManager
import com.example.marchenandroid.data.domain.User
import com.example.marchenandroid.data.network.ApiClient
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.data.network.dto.responses.FairytaleGetResponse
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val _fullname = MutableLiveData<String>()
    val fullname: LiveData<String> = _fullname

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _userRole = MutableLiveData<Int>()
    val userRole: LiveData<Int> = _userRole

    private val _navigateToSelectedFairytale = MutableLiveData<FairytaleGetResponse>()
    val navigateToSelectedFairytale: LiveData<FairytaleGetResponse> = _navigateToSelectedFairytale

    private val _tales = MutableLiveData<List<FairytaleGetResponse>>()
    val tales: LiveData<List<FairytaleGetResponse>> = _tales

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private var apiClient: ApiClient
    private var sessionManager: SessionManager
    private var _token: String
    private var _id: Int = 0

    init {
        apiClient = ApiClient()
        sessionManager = SessionManager(getApplication())
        _user.value = sessionManager.fetchUserData()
        _userRole.value = sessionManager.fetchUserRole()!!
        _token = sessionManager.fetchAuthToken()!!
        getId()
        getFairyTales()
    }

    private fun getId() {
        viewModelScope.launch {
            try {
                _id = apiClient.getApiService().getTeacherId("Bearer $_token")
                Log.i("API", "Procedure: TeacherGetId Value: ${_id}")
            } catch (e: Exception) {
                Log.i("API", "Procedure: TeacherGetId Error: ${e}")
            }
        }
    }

    fun getTeacherId(): Int {
        return _id
    }

    fun quit() {
        sessionManager.removeAllData()
    }

    private fun getFairyTales() {
        viewModelScope.launch {
            val apiClient = ApiClient()
            _status.value = ApiStatus.LOADING
            try {
                _tales.value = apiClient.getApiService().getFairyTales(topCount = 5, token = "Bearer $_token")
                _status.value = ApiStatus.DONE
                Log.i("API", "Procedure: GET Fairytales Value: ${_tales.value}")
            } catch (e: Exception) {
                Log.i("API", "Procedure: Fairytales Error: $e")
                _status.value = ApiStatus.ERROR
                _tales.value = ArrayList()
            }
        }
    }

    fun saveSelectedFairytaleToSP(fairytale: FairytaleGetResponse) {
        sessionManager.saveFairytale(fairytale)
    }

    fun displayFairytaleDetails(fairytale: FairytaleGetResponse) {
        _navigateToSelectedFairytale.value = fairytale
    }

    fun displayFairytaleDetailsComplete() {
        _navigateToSelectedFairytale.value = null
    }
}