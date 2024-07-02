package com.kashapovrush.utils.preferences

import android.content.SharedPreferences

interface PreferencesManager {
    fun getPreferences(): SharedPreferences

    fun getBoolean(key: String): Boolean

    fun putBoolean(key: String, value: Boolean )

    fun putString(key: String, value: String)

    fun getString(key: String): String?

    fun putLong(key: String, value: Long)

    fun getLong(key: String): Long

    fun clear()
}