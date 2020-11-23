package com.example.marchenandroid.ui.awards

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marchenandroid.data.SessionManager
import com.example.marchenandroid.data.network.ApiClient
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.data.network.dto.responses.AwardResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class AwardsViewModel(application: Application) : AndroidViewModel(application) {
    private val _childId = MutableLiveData<Int>()
    val childId: LiveData<Int> = _childId

    private val _awards = MutableLiveData<List<AwardResponse>>()
    val awards: LiveData<List<AwardResponse>> = _awards

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private var apiClient: ApiClient
    private var sessionManager: SessionManager
    private var _token: String

    init {
        _awards.value = null
        apiClient = ApiClient()
        sessionManager = SessionManager(getApplication())
        _token = sessionManager.fetchAuthToken()!!

        _childId.value = sessionManager.fetchChildId()!!
        sessionManager.removeChildId()

        if (_childId.value != 0) {
            getChildAwards(_childId.value!!)
        }
    }

    private fun getChildAwards(childId: Int) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            val apiClient = ApiClient()

            try {
                _awards.value = apiClient.getApiService().getChildAwards(childId, "Bearer $_token")
                for (a in _awards.value!!) {
                    a.AwardURL = "https://1.bp.blogspot.com/_16lyaJiGldI/TBekxqet-JI/AAAAAAAAAis/jTGCN4Wfo8Q/s1600/smile.jpg"
                }
                _status.value = ApiStatus.DONE
                Log.i("API", "Procedure: GET Child Awards Value: ${_awards.value}")
            } catch (e: Exception) {
                Log.i("API", "Procedure: GET Child Awards Error: $e")
                _status.value = ApiStatus.ERROR
                _awards.value = ArrayList()
            }
        }
    }
}