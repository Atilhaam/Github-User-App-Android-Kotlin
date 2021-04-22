package com.ilham.githubuserapp

import android.content.Context

internal class UserPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val IS_CHECKED = "isChecked"
    }
    private val prefrences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setPreference(value: AlarmModel) {
        val editor = prefrences.edit()
        editor.putBoolean(IS_CHECKED, value.isChecked)
        editor.apply()
    }
    fun getPreference() : AlarmModel {
        val model = AlarmModel()
        model.isChecked = prefrences.getBoolean(IS_CHECKED, false)
        return model
    }
}