package com.pauldelgado.neuropuzzles.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.pauldelgado.neuropuzzles.R

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val level = intent.getStringExtra(EXTRA_LEVEL).orEmpty()
        val attempts = intent.getIntExtra(EXTRA_ATTEMPTS, 0)
        val elapsed = intent.getIntExtra(EXTRA_ELAPSED, 0)
        val completed = intent.getBooleanExtra(EXTRA_COMPLETED, false)

        val tvStats = findViewById<TextView>(R.id.tvStats)
        val status = if (completed) "Completado" else "Tiempo terminado"
        tvStats.text = "Nivel: $level\nEstado: $status\nIntentos: $attempts\nTiempo: ${elapsed}s"

        findViewById<MaterialButton>(R.id.btnPlayAgain).setOnClickListener {
            startActivity(Intent(this, LevelSelectActivity::class.java))
            finish()
        }

        findViewById<MaterialButton>(R.id.btnHome).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    companion object {
        const val EXTRA_LEVEL = "extra_level"
        const val EXTRA_ATTEMPTS = "extra_attempts"
        const val EXTRA_ELAPSED = "extra_elapsed"
        const val EXTRA_COMPLETED = "extra_completed"
    }
}
