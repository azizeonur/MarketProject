package com.example.getir.domain.auth

import android.content.SharedPreferences
import javax.inject.Inject

class AuthPrefs @Inject constructor(
    private val prefs: SharedPreferences
) {

    fun saveUser(userId: String, token: String) {
        prefs.edit()
            .putString("user_id", userId)
            .putString("token", token)
            .apply()
    }

    fun getUserId(): String? =
        prefs.getString("user_id", null)

    fun clear() {
        prefs.edit().clear().apply()
    }
}