package com.pauldelgado.neuropuzzles.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.pauldelgado.neuropuzzles.R
import com.pauldelgado.neuropuzzles.data.AppSettings
import com.pauldelgado.neuropuzzles.data.Level

class LevelSelectActivity : AppCompatActivity() {

    private lateinit var settings: AppSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_select)
        settings = AppSettings(this)

        val btnEasy = findViewById<MaterialButton>(R.id.btnEasy)
        val btnMedium = findViewById<MaterialButton>(R.id.btnMedium)
        val btnHard = findViewById<MaterialButton>(R.id.btnHard)

        btnMedium.isEnabled = !settings.lockMedium
        btnHard.isEnabled = !settings.lockHard

        btnEasy.setOnClickListener { launch(Level.EASY) }
        btnMedium.setOnClickListener {
            if (!settings.lockMedium) launch(Level.MEDIUM) else Toast.makeText(this, "Nivel Medio bloqueado", Toast.LENGTH_SHORT).show()
        }
        btnHard.setOnClickListener {
            if (!settings.lockHard) launch(Level.HARD) else Toast.makeText(this, "Nivel Difícil bloqueado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun launch(level: Level) {
        val i = Intent(this, GameActivity::class.java)
        i.putExtra(GameActivity.EXTRA_LEVEL, level.name)
        startActivity(i)
    }
}
