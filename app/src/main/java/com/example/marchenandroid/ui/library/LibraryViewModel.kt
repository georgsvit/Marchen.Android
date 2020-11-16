package com.example.marchenandroid.ui.library

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marchenandroid.data.SessionManager
import com.example.marchenandroid.data.network.ApiClient
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.data.network.dto.responses.FairytaleGetResponse
import kotlinx.coroutines.launch

class LibraryViewModel(application: Application) : AndroidViewModel(application) {
    private val _navigateToSelectedFairytale = MutableLiveData<FairytaleGetResponse>()
    val navigateToSelectedFairytale: LiveData<FairytaleGetResponse> = _navigateToSelectedFairytale

    private val _tales = MutableLiveData<List<FairytaleGetResponse>>()
    val tales: LiveData<List<FairytaleGetResponse>> = _tales

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private lateinit var sessionManager: SessionManager
    private lateinit var _token: String

    init {
        _tales.value = null
        sessionManager = SessionManager(getApplication())
        _token = sessionManager.fetchAuthToken()!!

        getFairyTales()
    }

    private fun getFairyTales() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            val apiClient = ApiClient()

            try {
                _tales.value = apiClient.getApiService().getFairyTales("Bearer $_token")
                _status.value = ApiStatus.DONE
                Log.i("API", "Procedure: GET Fairytales Value: ${_tales.value}")
            } catch (e: Exception) {
                Log.i("API", "Procedure: Fairytales Error: $e")
                _status.value = ApiStatus.ERROR
                _tales.value = ArrayList()
            }
        }
    }

    fun displayFairytaleDetails(fairytale: FairytaleGetResponse) {
        _navigateToSelectedFairytale.value = fairytale
    }

    fun displayFairytaleDetailsComplete() {
        _navigateToSelectedFairytale.value = null
    }
}