package com.pauldelgado.neuropuzzles.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.pauldelgado.neuropuzzles.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<MaterialButton>(R.id.btnStart).setOnClickListener {
            startActivity(Intent(this, LevelSelectActivity::class.java))
        }

        findViewById<MaterialButton>(R.id.btnAdmin).setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }
    }
}
