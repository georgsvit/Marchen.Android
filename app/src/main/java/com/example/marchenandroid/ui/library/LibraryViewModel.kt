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
import java.util.*
import kotlin.collections.ArrayList

class LibraryViewModel(application: Application) : AndroidViewModel(application) {
    private val _navigateToSelectedFairytale = MutableLiveData<FairytaleGetResponse>()
    val navigateToSelectedFairytale: LiveData<FairytaleGetResponse> = _navigateToSelectedFairytale

    private val _tales = MutableLiveData<List<FairytaleGetResponse>>()
    val tales: LiveData<List<FairytaleGetResponse>> = _tales

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private var sessionManager: SessionManager
    private var _token: String

    var selectedItem = -1
    var minAge = 0
    var maxAge = 10
    lateinit var types: List<String>

    init {
        _tales.value = null
        sessionManager = SessionManager(getApplication())
        _token = sessionManager.fetchAuthToken()!!

        getProblems()
        getFairyTales()
    }

    private fun getProblems() {
        viewModelScope.launch {
            val apiClient = ApiClient()
            try {
                types = apiClient.getApiService().getPsychoTypes(token = "Bearer $_token")
                Log.i("API", "Procedure: GET Psychotypes Value: ${types}")
            } catch (e: Exception) {
                Log.i("API", "Procedure: Psychotypes Error: $e")
                types = listOf("Undefined", "Undefined", "Undefined")
            }
        }
    }

    fun getFilteredFairyTales() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            val apiClient = ApiClient()

            try {

                if (selectedItem != -1 && (maxAge != 10 || minAge != 0)) {
                    _tales.value = apiClient.getApiService().getFairyTales(psychoType = types[selectedItem], maxAge = maxAge, minAge = minAge, token = "Bearer $_token")
                } else if (selectedItem != -1) {
                    _tales.value = apiClient.getApiService().getFairyTales(psychoType = types[selectedItem], token = "Bearer $_token")
                } else if (maxAge != 10 || minAge != 0) {
                    _tales.value = apiClient.getApiService().getFairyTales(maxAge = maxAge, minAge = minAge, token = "Bearer $_token")
                } else {
                    _tales.value = apiClient.getApiService().getFairyTales(token = "Bearer $_token")
                }

                _status.value = ApiStatus.DONE
                Log.i("API", "Procedure: GET Fairytales Value: ${_tales.value}")
            } catch (e: Exception) {
                Log.i("API", "Procedure: Fairytales Error: $e")
                _status.value = ApiStatus.ERROR
                _tales.value = ArrayList()
            }
        }
    }

    private fun getFairyTales() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            val apiClient = ApiClient()

            try {
                _tales.value = apiClient.getApiService().getFairyTales(token = "Bearer $_token")
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