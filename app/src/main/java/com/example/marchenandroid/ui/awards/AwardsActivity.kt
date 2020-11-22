package com.example.marchenandroid.ui.awards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.marchenandroid.R

class AwardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_awards)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        if (actionBar != null) {
            actionBar.title = "Child Awards"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }
}