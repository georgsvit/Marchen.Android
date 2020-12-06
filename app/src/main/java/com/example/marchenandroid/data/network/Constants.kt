package com.example.marchenandroid.data.network

object Constants {
    const val BASE_URL = "https://marchen.azurewebsites.net/"
    const val LOGIN_URL = "users/login"
    const val REGISTER_URL = "users/register"
    const val TEACHER_ID_URL = "users/account/id"
    const val CHILDREN_URL = "children"
    const val DELETE_URL = "users"
    const val FAIRYTALES_URL = "fairytales?{psychoType}&{minAge}&{maxAge}&{topCount}"
    const val UNIT_URL = "units/{unitId}/{childId}"
    const val SAVEPOINTS_URL = "savepoints/{fairytaleId}"
    const val REPORTS_URL = "/reports"
    const val REPORT_DOWNLOAD_URL = "/reports/{savepointId}"
    const val CHILD_ID_URL = "/{childId}"
    const val AWARDS_URL = "/awards"
    const val AVATARS_URL = "/avatars"
    const val DELETE_FOR_GROUP_URL = "/removeFromGroup"
    const val FAIRYTALE_AWARD = "/awards/{fairytaleId}"
    const val PSYCHOTYPES_URL = "fairytales/psychoTypes"
}