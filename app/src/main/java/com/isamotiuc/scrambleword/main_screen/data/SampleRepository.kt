package com.isamotiuc.scrambleword.main_screen.data

import android.content.SharedPreferences
import javax.inject.Inject

class SampleRepository @Inject constructor(val sharedPreferences: SharedPreferences) {

    fun getLong(key: String): Long = sharedPreferences.getLong(key, 0)

    fun putLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun getInt(key: String): Int = sharedPreferences.getInt(key, 0)

    fun putInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getString(key: String): String = sharedPreferences.getString(key, "")

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}