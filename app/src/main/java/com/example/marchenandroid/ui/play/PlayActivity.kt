package com.example.marchenandroid.ui.play

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marchenandroid.R

class PlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        supportActionBar?.hide()
    }
}