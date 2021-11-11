package com.grossosdevelopopoyos.fuji.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.grossosdevelopopoyos.fuji.R

class AppearanceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.appearance_settings, rootKey)

        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val listener: SharedPreferences.OnSharedPreferenceChangeListener =
            SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
                if (key == "darkmode") {
                    val ujm: SwitchPreference = findPreference("darkmode")!!
                    val darkModeSwitch: Boolean = prefs.getBoolean("darkmode", false)

                    if (darkModeSwitch) {
                        ujm.isChecked = true
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        ujm.isChecked = false
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        prefs.registerOnSharedPreferenceChangeListener(listener)

    }
}