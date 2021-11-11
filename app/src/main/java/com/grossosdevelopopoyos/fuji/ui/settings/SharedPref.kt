package com.grossosdevelopopoyos.fuji.ui.settings

import android.content.Context
import android.content.SharedPreferences
import com.grossosdevelopopoyos.fuji.MainActivity

class SharedPref(context: MainActivity) {
    internal lateinit var mySharedPref: SharedPreferences

    init {
        mySharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE)
    }

    fun setNightModeState (state: Boolean?) {
        val editor: SharedPreferences.Editor = mySharedPref.edit()
        editor.putBoolean("Night Mode", state!!)
        editor.commit()
    }

    fun loadNightModeState(): Boolean {
        return mySharedPref.getBoolean("Night Mode", false)
    }
}