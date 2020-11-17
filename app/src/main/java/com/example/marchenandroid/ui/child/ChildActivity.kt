package com.example.marchenandroid.ui.child

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.marchenandroid.R
import kotlinx.android.synthetic.main.fragment_details.*

class ChildActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        if (actionBar != null) {
            actionBar.title = "Child Profile"
        }
        val dividerItemDecoration =  DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
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