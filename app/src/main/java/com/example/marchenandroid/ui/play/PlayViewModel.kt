package com.example.marchenandroid.ui.play

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marchenandroid.data.SessionManager
import com.example.marchenandroid.data.network.ApiClient
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.data.network.dto.responses.SlideResponse
import com.example.marchenandroid.data.network.dto.responses.UnitGetResponse
import kotlinx.coroutines.launch

class PlayViewModel(application: Application) : AndroidViewModel(
    application
) {
    private var _childId: Int = 0
    private var _firstUnitId: Int = 0

    private val _currentUnitId = MutableLiveData<Int>()
    val currentUnitId: LiveData<Int> = _currentUnitId

    private var currentSlidePos: Int = 0

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private val _slide = MutableLiveData<SlideResponse>()
    val slide: LiveData<SlideResponse> = _slide

    private val _hasNextSlide = MutableLiveData<Boolean>()
    val hasNextSlide: LiveData<Boolean> = _hasNextSlide

    private val _hasPrevSlide = MutableLiveData<Boolean>()
    val hasPrevSlide: LiveData<Boolean> = _hasPrevSlide

    private val _question = MutableLiveData<Boolean>()
    val question: LiveData<Boolean> = _question

    private val _unit = MutableLiveData<UnitGetResponse>()
    val unit: LiveData<UnitGetResponse> = _unit

    private var sessionManager: SessionManager
    private var _token: String

    init {
        sessionManager = SessionManager(getApplication())
        _token = sessionManager.fetchAuthToken()!!


        _childId = sessionManager.fetchChildId()!!
        _firstUnitId = sessionManager.fetchUnitId()!!

        sessionManager.removeChildId()
        sessionManager.removeUnitId()

        _hasPrevSlide.value = false
        _currentUnitId.value = _firstUnitId
        getUnit(_currentUnitId.value!!)
    }

    private fun setSlide() {
        if (currentSlidePos == _unit.value!!.Slides.count()) {
            _question.value = true
        } else {
            _slide.value = _unit.value!!.Slides[currentSlidePos]
            _question.value = false
        }
        _hasNextSlide.value = currentSlidePos != _unit.value!!.Slides.count()
        _hasPrevSlide.value = currentSlidePos != 0
    }

    fun nextSlide() {
        currentSlidePos++
        setSlide()
    }

    fun prevSlide() {
        currentSlidePos--
        setSlide()
    }

    fun optionOnClick(Answear: Int) {
        getUnit(Answear)
    }

    private fun getUnit(unitId: Int) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            val apiClient = ApiClient()

            try {
                _unit.value = apiClient.getApiService().getUnitById(unitId, _childId,"Bearer $_token")
                _status.value = ApiStatus.DONE
                currentSlidePos = 0
                setSlide()
                Log.i("API", "Procedure: GET UnitById Value: ${_unit.value}")
            } catch (e: Exception) {
                Log.i("API", "Procedure: GET UnitById Error: $e")
                _status.value = ApiStatus.ERROR
                _unit.value = null
            }
        }
    }
}