package com.example.marchenandroid.data

import android.content.Context
import android.content.SharedPreferences
import com.example.marchenandroid.R
import com.example.marchenandroid.data.domain.User
import com.example.marchenandroid.data.network.dto.responses.FairytaleGetResponse
import com.example.marchenandroid.data.network.dto.responses.JWTTokenResponse
import java.text.SimpleDateFormat
import java.util.*

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.app_name),
        Context.MODE_PRIVATE
    )

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_NAME = "user_name"
        const val USER_ROLE = "user_role"
        const val TOKEN_DATE = "token_date"
    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun saveTokenDate(date: Date) {
        val editor = prefs.edit()
        editor.putString(TOKEN_DATE, date.toString())
        editor.apply()
    }

    fun saveAllData(jwtTokenResponse: JWTTokenResponse) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, jwtTokenResponse.AccessToken)
        editor.putString(USER_NAME, jwtTokenResponse.Fullname)
        editor.putInt(
            USER_ROLE, when (jwtTokenResponse.RoleName) {
                "Teacher" -> 1
                "Parent" -> 2
                else -> -1
            }
        )
        editor.putString(TOKEN_DATE, jwtTokenResponse.ExpiresAt.toString())
        editor.apply()
    }

    fun saveUnitId(unitId: Int) {
        val editor = prefs.edit()
        editor.putInt("unitId", unitId)
        editor.apply()
    }

    fun saveChildId(childId: Int) {
        val editor = prefs.edit()
        editor.putInt("childId", childId)
        editor.apply()
    }

    fun saveFairytale(fairytale: FairytaleGetResponse){
        val editor = prefs.edit()
        editor.putInt("fairytaleId", fairytale.Id)
        editor.putString("fairytaleName", fairytale.Name)
        editor.putString("fairytaleContents", fairytale.Contents)
        editor.putString("fairytalePsychoType", fairytale.PsychoType)
        editor.putInt("fairytaleFirstUnitId", fairytale.FirstUnitId)
        editor.putString("fairytalePictureURL", fairytale.PictureURL)
        editor.putInt("fairytaleMinAge", fairytale.MinAge)
        editor.putInt("fairytaleMaxAge", fairytale.MaxAge)
        editor.putString("fairytaleCreationDate", fairytale.CreationDate)
        editor.apply()
    }

    fun removeChildId() {
        val editor = prefs.edit().remove("childId")
        editor.apply()
    }

    fun removeUnitId() {
        val editor = prefs.edit().remove("unitId")
        editor.apply()
    }

    fun removeFairytaleId() {
        val editor = prefs.edit().remove("fairytaleId")
        editor.apply()
    }

    fun removeFairytale() {
        val editor = prefs.edit()
        editor.remove("fairytaleId")
        editor.remove("fairytaleName")
        editor.remove("fairytaleContents")
        editor.remove("fairytalePsychoType")
        editor.remove("fairytaleFirstUnitId")
        editor.remove("fairytalePictureURL")
        editor.apply()
    }

    fun removeAuthToken() {
        val editor = prefs.edit().remove(USER_TOKEN)
        editor.apply()
    }

    fun removeAllData() {
        val editor = prefs.edit().remove(USER_TOKEN).remove(USER_NAME).remove(USER_ROLE).remove(
            TOKEN_DATE
        )
        editor.apply()
    }

    fun fetchUnitId() : Int? {
        return prefs.getInt("unitId", 0)
    }

    fun fetchChildId() : Int? {
        return prefs.getInt("childId", 0)
    }

    fun fetchFairytaleId() : Int? {
        return prefs.getInt("fairytaleId", 0)
    }

    fun fetchFairytale() : FairytaleGetResponse? {
        return FairytaleGetResponse(
                prefs.getInt("fairytaleId", -1),
                prefs.getString("fairytaleName", null)!!,
                prefs.getString("fairytaleContents", null)!!,
                prefs.getString("fairytalePsychoType", null)!!,
                prefs.getInt("fairytaleFirstUnitId", -1),
                prefs.getString("fairytalePictureURL", null)!!,
                prefs.getInt("fairytaleMinAge", -1),
                prefs.getInt("fairytaleMaxAge", -1),
                prefs.getString("fairytaleCreationDate", null)!!
        )
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun fetchUserRole(): Int? {
        return prefs.getInt(USER_ROLE, 0)
    }

    fun fetchUserData(): User? {
        return User(
            -1,
            "",
            "",
            prefs.getString(USER_NAME, null)!!,
            prefs.getInt(USER_ROLE, 0),
            null
        )
    }

    fun fetchDate(dateName: String): Date? {
        val formatter = SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy", Locale.ENGLISH)
        val dateInString = prefs.getString(dateName, null)
        return if (dateInString != null) formatter.parse(dateInString) else null
    }
}