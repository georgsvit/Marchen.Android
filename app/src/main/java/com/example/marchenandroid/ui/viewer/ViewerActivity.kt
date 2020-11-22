package com.example.marchenandroid.ui.viewer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.marchenandroid.R
import com.example.marchenandroid.data.SessionManager
import com.example.marchenandroid.data.network.ApiClient
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class ViewerActivity : AppCompatActivity() {

    var uri: Uri? = null
    var reportId: Int = 0

    private val READ_REQUEST_CODE = 42
    private val WRITE_REQUEST_CODE = 43

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)

        reportId = intent.getIntExtra("reportId", 0)

        createFile("application/pdf", "report $reportId")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
            if (resultData != null) {
                uri = resultData.data
                Log.i("URI", "Uri: " + uri.toString())

                getReport(reportId)
            }

        super.onActivityResult(requestCode, resultCode, resultData)
    }

    private fun createFile(mimeType: String, fileName: String) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = mimeType
        intent.putExtra(Intent.EXTRA_TITLE, fileName)
        startActivityForResult(intent, WRITE_REQUEST_CODE)
    }

    private fun getReport(reportId: Int) {
        lifecycleScope.launch {
            val apiClient = ApiClient()
            val sessionManager = SessionManager(context = application)
            val token = sessionManager.fetchAuthToken()
            try {
                val body = apiClient.getApiService().getReportDownloadLink(reportId, "Bearer $token")
                alterDocument(uri!!, body)
            } catch (e: Exception) {
                Log.i("API", "Procedure: Login Error: ${e}")
            }
        }
    }

    private fun alterDocument(uri: Uri, body: ResponseBody) {
        try {
            val stream = body.byteStream()
            val pfd: ParcelFileDescriptor =
                this.contentResolver.openFileDescriptor(uri, "w")!!
            val fileOutputStream = FileOutputStream(pfd.fileDescriptor)
            fileOutputStream.use { fileOutputStream ->
                stream.copyTo(fileOutputStream)
            }
            fileOutputStream.close()
            pfd.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        this.finish()
    }
}