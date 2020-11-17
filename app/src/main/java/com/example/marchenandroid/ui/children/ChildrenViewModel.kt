package com.example.marchenandroid.ui.children

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marchenandroid.data.SessionManager
import com.example.marchenandroid.data.network.ApiClient
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.data.network.dto.responses.ChildResponse
import kotlinx.coroutines.launch

class ChildrenViewModel(application: Application) : AndroidViewModel(application) {
    private val _navigateToSelectedChild = MutableLiveData<ChildResponse>()
    val navigateToSelectedChild: LiveData<ChildResponse> = _navigateToSelectedChild

    private val _children = MutableLiveData<List<ChildResponse>>()
    val children: LiveData<List<ChildResponse>> = _children

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private var sessionManager: SessionManager
    private var _token: String

    init {
        _children.value = null
        sessionManager = SessionManager(getApplication())
        _token = sessionManager.fetchAuthToken()!!

        getChildren()
    }

    fun saveChildIdToSP(childId: Int) {
        sessionManager.saveChildId(childId)
    }

    fun globalGetChildren() {
        getChildren()
    }

    private fun getChildren() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            val apiClient = ApiClient()

            try {
                _children.value = apiClient.getApiService().getChildren("Bearer $_token")
                _status.value = ApiStatus.DONE
                Log.i("API", "Procedure: GET Children Value: ${_children.value}")
            } catch (e: Exception) {
                Log.i("API", "Procedure: Children Error: $e")
                _status.value = ApiStatus.ERROR
                _children.value = ArrayList()
            }
        }
    }

    fun displayChildDetails(child: ChildResponse) {
        _navigateToSelectedChild.value = child
    }

    fun displayChildDetailsComplete() {
        _navigateToSelectedChild.value = null
    }
}