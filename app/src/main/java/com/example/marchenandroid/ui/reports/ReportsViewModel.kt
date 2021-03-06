package com.example.marchenandroid.ui.reports

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marchenandroid.data.SessionManager
import com.example.marchenandroid.data.network.ApiClient
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.data.network.dto.responses.ChildReportResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class ReportsViewModel(application: Application) : AndroidViewModel(application) {
    private val _childId = MutableLiveData<Int>()
    val childId: LiveData<Int> = _childId

    private val _reports = MutableLiveData<List<ChildReportResponse>>()
    val reports: LiveData<List<ChildReportResponse>> = _reports

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private var apiClient: ApiClient
    private var sessionManager: SessionManager
    private var _token: String

    init {
        _reports.value = null
        apiClient = ApiClient()
        sessionManager = SessionManager(getApplication())
        _token = sessionManager.fetchAuthToken()!!

        _childId.value = sessionManager.fetchChildId()!!

        if (_childId.value != 0) {
            getChildReports(_childId.value!!)
        }
    }

    private fun getChildReports(childId: Int) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            val apiClient = ApiClient()

            try {
                _reports.value = apiClient.getApiService().getChildReports(childId, "Bearer $_token")
                _status.value = ApiStatus.DONE
                Log.i("API", "Procedure: GET Child Reports Value: ${_reports.value}")
            } catch (e: Exception) {
                Log.i("API", "Procedure: GET Child Reports Error: $e")
                _status.value = ApiStatus.ERROR
                _reports.value = ArrayList()
            }
        }
    }
}