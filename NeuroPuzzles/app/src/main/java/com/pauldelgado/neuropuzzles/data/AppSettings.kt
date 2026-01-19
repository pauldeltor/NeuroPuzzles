package com.pauldelgado.neuropuzzles.data

import android.content.Context
import android.content.SharedPreferences

class AppSettings(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var sessionSeconds: Int
        get() = prefs.getInt(KEY_SESSION_SECONDS, DEFAULT_SESSION_SECONDS)
        set(value) = prefs.edit().putInt(KEY_SESSION_SECONDS, value.coerceIn(30, 3600)).apply()

    var previewSeconds: Int
        get() = prefs.getInt(KEY_PREVIEW_SECONDS, DEFAULT_PREVIEW_SECONDS)
        set(value) = prefs.edit().putInt(KEY_PREVIEW_SECONDS, value.coerceIn(0, 30)).apply()

    var lockMedium: Boolean
        get() = prefs.getBoolean(KEY_LOCK_MEDIUM, false)
        set(value) = prefs.edit().putBoolean(KEY_LOCK_MEDIUM, value).apply()

    var lockHard: Boolean
        get() = prefs.getBoolean(KEY_LOCK_HARD, false)
        set(value) = prefs.edit().putBoolean(KEY_LOCK_HARD, value).apply()

    var feedbackEnabled: Boolean
        get() = prefs.getBoolean(KEY_FEEDBACK, true)
        set(value) = prefs.edit().putBoolean(KEY_FEEDBACK, value).apply()

    companion object {
        private const val PREFS_NAME = "neuropuzzles_prefs"

        private const val KEY_SESSION_SECONDS = "session_seconds"
        private const val KEY_PREVIEW_SECONDS = "preview_seconds"
        private const val KEY_LOCK_MEDIUM = "lock_medium"
        private const val KEY_LOCK_HARD = "lock_hard"
        private const val KEY_FEEDBACK = "feedback"

        private const val DEFAULT_SESSION_SECONDS = 180
        private const val DEFAULT_PREVIEW_SECONDS = 3
    }
}
