package com.example.marchenandroid.data.network

import com.example.marchenandroid.data.network.dto.requests.ChildRegisterRequest
import com.example.marchenandroid.data.network.dto.requests.LoginRequest
import com.example.marchenandroid.data.network.dto.requests.RegisterRequest
import com.example.marchenandroid.data.network.dto.responses.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest) : JWTTokenResponse

    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest) : JWTTokenResponse

    @POST(Constants.CHILDREN_URL)
    suspend fun childRegister(@Header("Authorization") token: String, @Body request: ChildRegisterRequest) : ResponseBody

    @GET(Constants.FAIRYTALES_URL)
    suspend fun getFairyTales(@Header("Authorization") token: String) : List<FairytaleGetResponse>

    @GET(Constants.UNIT_URL)
    suspend fun getUnitById(@Path("unitId") unitId: Int, @Path("childId") childId: Int, @Header("Authorization") token: String) : UnitGetResponse

    @GET(Constants.SAVEPOINTS_URL)
    suspend fun getSavepoints(@Path("fairytaleId") fairytaleId: Int, @Header("Authorization") token: String) : List<SavepointResponse>

    @GET(Constants.CHILDREN_URL)
    suspend fun getChildren(@Header("Authorization") token: String) : List<ChildResponse>

    @GET(Constants.CHILDREN_URL + Constants.CHILD_REPORTS_URL)
    suspend fun getChildReports(@Path("childId") childId: Int, @Header("Authorization") token: String) : List<ChildReportResponse>

    @GET(Constants.REPORT_DOWNLOAD_URL)
    suspend fun getReportDownloadLink(@Path("savepointId") childId: Int, @Header("Authorization") token: String) : ResponseBody//Call<ResponseBody>

    @DELETE(Constants.DELETE_URL)
    fun deleteUser(@Header("Authorization") token: String) : Call<ResponseBody>
}