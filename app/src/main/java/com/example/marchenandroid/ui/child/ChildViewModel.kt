package com.example.marchenandroid.ui.child

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
import com.example.marchenandroid.data.network.dto.responses.ChildResponse
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class ChildViewModel(application: Application) : AndroidViewModel(application) {
    private val _childId = MutableLiveData<Int>()
    val childId: LiveData<Int> = _childId

    private val _child = MutableLiveData<ChildResponse>()
    val child: LiveData<ChildResponse> = _child

    private val _userRole = MutableLiveData<Int>()
    val userRole: LiveData<Int> = _userRole

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _deleteStatus = MutableLiveData<ApiStatus>()
    val deleteStatus: LiveData<ApiStatus> = _deleteStatus

    private var apiClient: ApiClient
    private var sessionManager: SessionManager
    private var _token: String

    init {
        apiClient = ApiClient()
        sessionManager = SessionManager(getApplication())
        _token = sessionManager.fetchAuthToken()!!
        _userRole.value = sessionManager.fetchUserRole()

        _childId.value = sessionManager.fetchChildId()!!

        if (_childId.value != 0) {
            getChild(_childId.value!!)
        }
    }

    fun saveChildIdToSP() {
        sessionManager.saveChildId(_childId.value!!)
    }

    fun globalGetChild() {
        getChild(_childId.value!!)
    }

    private fun getChild(childId: Int) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            val apiClient = ApiClient()

            try {
                _child.value = apiClient.getApiService().getChildById(childId, "Bearer $_token")
                _status.value = ApiStatus.DONE
                Log.i("API", "Procedure: GET Child Value: ${_child.value}")
            } catch (e: Exception) {
                Log.i("API", "Procedure: GET Child Error: $e")
                _status.value = ApiStatus.ERROR
                _child.value = ChildResponse(-1, "undefined", "undefined", "undefined", -1, "")
            }
        }
    }

    fun deleteFromGroup() {
        viewModelScope.launch {
            _deleteStatus.value = ApiStatus.LOADING
            val apiClient = ApiClient()

            try {
                val response = apiClient.getApiService().deleteChildFromGroup(_childId.value!!, "Bearer $_token")
                _deleteStatus.value = ApiStatus.DONE
                Log.i("API", "Procedure: DELETE Child Value: ${response}")
            } catch (e: Exception) {
                Log.i("API", "Procedure: DELETE Child Error: $e")
                _deleteStatus.value = ApiStatus.ERROR
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            _deleteStatus.value = ApiStatus.LOADING
            val apiClient = ApiClient()

            try {
                val response = apiClient.getApiService().deleteChild(_childId.value!!, "Bearer $_token")
                _deleteStatus.value = ApiStatus.DONE
                Log.i("API", "Procedure: DELETE Child Value: ${response}")
            } catch (e: Exception) {
                Log.i("API", "Procedure: DELETE Child Error: $e")
                _deleteStatus.value = ApiStatus.ERROR
            }
        }
    }
}