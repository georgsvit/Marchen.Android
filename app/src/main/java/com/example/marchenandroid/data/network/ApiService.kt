package com.example.marchenandroid.data.network

import com.example.marchenandroid.data.network.dto.requests.ChildRequest
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
    suspend fun childRegister(@Header("Authorization") token: String, @Body request: ChildRequest) : ResponseBody

    @PATCH(Constants.CHILDREN_URL + Constants.CHILD_ID_URL)
    suspend fun childEdit(@Path("childId") childId: Int, @Header("Authorization") token: String, @Body request: ChildRequest) : ResponseBody

    @GET(Constants.TEACHER_ID_URL)
    suspend fun getTeacherId(@Header("Authorization") token: String) : Int

    @GET(Constants.FAIRYTALES_URL)
    suspend fun getFairyTales(@Header("Authorization") token: String) : List<FairytaleGetResponse>

    @GET(Constants.UNIT_URL)
    suspend fun getUnitById(@Path("unitId") unitId: Int, @Path("childId") childId: Int, @Header("Authorization") token: String) : UnitGetResponse

    @GET(Constants.SAVEPOINTS_URL)
    suspend fun getSavepoints(@Path("fairytaleId") fairytaleId: Int, @Header("Authorization") token: String) : List<SavepointResponse>

    @GET(Constants.CHILDREN_URL)
    suspend fun getChildren(@Header("Authorization") token: String) : List<ChildResponse>

    @GET(Constants.AVATARS_URL)
    suspend fun getAvatars(@Header("Authorization") token: String) : List<AvatarResponse>

    @GET(Constants.CHILDREN_URL + Constants.CHILD_ID_URL)
    suspend fun getChildById(@Path("childId") childId: Int, @Header("Authorization") token: String) : ChildResponse

    @GET(Constants.CHILDREN_URL + Constants.CHILD_ID_URL + Constants.REPORTS_URL)
    suspend fun getChildReports(@Path("childId") childId: Int, @Header("Authorization") token: String) : List<ChildReportResponse>

    @GET(Constants.CHILDREN_URL + Constants.CHILD_ID_URL + Constants.AWARDS_URL)
    suspend fun getChildAwards(@Path("childId") childId: Int, @Header("Authorization") token: String) : List<AwardResponse>

    @GET(Constants.REPORT_DOWNLOAD_URL)
    suspend fun getReportDownloadLink(@Path("savepointId") childId: Int, @Header("Authorization") token: String) : ResponseBody//Call<ResponseBody>

    @DELETE(Constants.CHILDREN_URL + Constants.CHILD_ID_URL)
    suspend fun deleteChild(@Path("childId") childId: Int, @Header("Authorization") token: String) : ResponseBody

    @DELETE(Constants.DELETE_URL)
    fun deleteUser(@Header("Authorization") token: String) : Call<ResponseBody>
}