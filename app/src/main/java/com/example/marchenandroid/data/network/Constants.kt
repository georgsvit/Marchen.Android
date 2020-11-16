package com.example.marchenandroid.data.network

object Constants {
    const val BASE_URL = "https://marchen.azurewebsites.net/"
    const val LOGIN_URL = "users/login"
    const val REGISTER_URL = "users/register"
    const val CHILDREN_URL = "children"
    const val DELETE_URL = "users"
    const val FAIRYTALES_URL = "fairytales"
    const val UNIT_URL = "units/{unitId}/{childId}"
    const val SAVEPOINTS_URL = "savepoints/{fairytaleId}"
    const val CHILD_REPORTS_URL = "/{childId}/reports"
    const val REPORT_DOWNLOAD_URL = "/reports/{savepointId}"
}