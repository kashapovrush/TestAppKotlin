package com.kashapovrush.utils.preferences

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceManagerImpl @Inject constructor(private val context: Context): PreferencesManager {

    override fun getPreferences(): SharedPreferences {
        return context.getSharedPreferences(EXTRA_PREFERENCES, Context.MODE_PRIVATE)
    }


    override fun putBoolean(key: String, value: Boolean) {
        val editor = getPreferences().edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    override fun getBoolean(key: String): Boolean {
        return getPreferences().getBoolean(key, false)
    }



    override fun putString(key: String, value: String) {
        val editor = getPreferences().edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun getString(key: String): String? {
        return getPreferences().getString(key, null)
    }

    override fun putLong(key: String, value: Long) {
        val editor = getPreferences().edit()
        editor.putLong(key, value)
        editor.apply()
    }

    override fun getLong(key: String): Long {
        return getPreferences().getLong(key, 0)
    }

    override fun clear() {
        val editor = getPreferences().edit()
        editor.clear()
        editor.apply()
    }

    companion object {

        const val EXTRA_PREFERENCES = "preferences"
    }
}
