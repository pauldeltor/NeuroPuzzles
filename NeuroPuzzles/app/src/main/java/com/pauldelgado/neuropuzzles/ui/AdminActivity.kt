package com.pauldelgado.neuropuzzles.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.pauldelgado.neuropuzzles.R
import com.pauldelgado.neuropuzzles.data.AppSettings

class AdminActivity : AppCompatActivity() {

    private lateinit var settings: AppSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        settings = AppSettings(this)

        val etSession = findViewById<TextInputEditText>(R.id.etSessionSeconds)
        val etPreview = findViewById<TextInputEditText>(R.id.etPreviewSeconds)
        val swLockMedium = findViewById<SwitchMaterial>(R.id.swLockMedium)
        val swLockHard = findViewById<SwitchMaterial>(R.id.swLockHard)
        val swFeedback = findViewById<SwitchMaterial>(R.id.swSound)

        etSession.setText(settings.sessionSeconds.toString())
        etPreview.setText(settings.previewSeconds.toString())
        swLockMedium.isChecked = settings.lockMedium
        swLockHard.isChecked = settings.lockHard
        swFeedback.isChecked = settings.feedbackEnabled

        findViewById<MaterialButton>(R.id.btnSave).setOnClickListener {
            val session = etSession.text?.toString()?.toIntOrNull() ?: settings.sessionSeconds
            val preview = etPreview.text?.toString()?.toIntOrNull() ?: settings.previewSeconds

            settings.sessionSeconds = session
            settings.previewSeconds = preview
            settings.lockMedium = swLockMedium.isChecked
            settings.lockHard = swLockHard.isChecked
            settings.feedbackEnabled = swFeedback.isChecked

            Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
