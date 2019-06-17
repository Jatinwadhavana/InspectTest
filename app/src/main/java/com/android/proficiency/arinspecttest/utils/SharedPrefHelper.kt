package com.android.proficiency.arinspecttest.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefHelper {
    private lateinit var pref: SharedPreferences
    private const val titleKey = "titleData"
    private const val pref_name = "appInspect"

    fun initPreference(context: Context) {
        pref = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE)
    }

    fun storeTitle(title: String) {
        val prefsEditor: SharedPreferences.Editor = pref.edit()
        with(prefsEditor) {
            putString(titleKey, title)
            commit()
        }
    }

    fun getTitle(): String? {
        return pref.getString(titleKey, "Title")
    }
}