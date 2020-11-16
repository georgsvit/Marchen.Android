package com.example.marchenandroid.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

class ApiClient {
    private lateinit var apiService: ApiService


    fun getApiService(): ApiService {
        if (!::apiService.isInitialized) {
            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService
    }

//    companion object {
//        val apiService : ApiService by lazy { retrofit.create(ApiService::class.java) }
//    }
}
