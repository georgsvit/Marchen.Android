package com.example.marchenandroid.ui.details

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
import com.example.marchenandroid.data.network.dto.responses.SavepointResponse
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val _selectedFairytale = MutableLiveData<FairytaleGetResponse>()
    var selectedFairytale: LiveData<FairytaleGetResponse> = _selectedFairytale

    private val _selectedChildSave = MutableLiveData<SavepointResponse>()
    var selectedChildSave: LiveData<SavepointResponse> = _selectedChildSave

    private val _saves = MutableLiveData<List<SavepointResponse>>()
    val saves: LiveData<List<SavepointResponse>> = _saves

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private var sessionManager: SessionManager
    private var _token: String

    init {
        _saves.value = null
        sessionManager = SessionManager(getApplication())
        _token = sessionManager.fetchAuthToken()!!

        _selectedFairytale.value = sessionManager.fetchFairytale()
        getSaves()
    }

    fun saveChildIdToSP() {
        sessionManager.saveChildId(selectedChildSave.value!!.ChildId)
    }

    fun saveUnitIdToSP() {
        sessionManager.saveUnitId(selectedChildSave.value!!.UnitId)
    }

    fun globalGetSaves() {
        getSaves()
    }

    private fun getSaves() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            val apiClient = ApiClient()

            try {
                _saves.value = apiClient.getApiService().getSavepoints(_selectedFairytale.value!!.Id, "Bearer $_token")
                _status.value = ApiStatus.DONE
                Log.i("API", "Procedure: GET Savepoints Value: ${_saves.value}")
            } catch (e: Exception) {
                Log.i("API", "Procedure: GET Savepoints Error: $e")
                _status.value = ApiStatus.ERROR
                _saves.value = ArrayList()
            }
        }
    }

    fun continueGame(save: SavepointResponse) {
        navigateToSelectedChildSave(save)
    }

    fun restartGame(save: SavepointResponse) {
        save.UnitId = _selectedFairytale.value!!.FirstUnitId
        navigateToSelectedChildSave(save)
    }

    private fun navigateToSelectedChildSave(save: SavepointResponse) {
        _selectedChildSave.value = save
    }

    fun navigationToSelectedChildSaveComplete() {
        _selectedChildSave.value = null
    }

}