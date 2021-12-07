package com.example.fuji.ui.settings

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.fuji.R

class SettingsFragment : PreferenceFragmentCompat() {

    private var mContext: Context? = null
    private var mActivity: Activity? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_screen, rootKey) // the settings XML here (not the layout)

        // Get the application context
        mContext = this.activity
        mActivity = this.activity

        // Config listener on switch dark mode
        createListenerOnSwitchDarkMode()
    }

    private fun createListenerOnSwitchDarkMode() {
        // Get the switch preference dark mode
        val onOffDarkMode: SwitchPreference? = findPreference(
            this.resources
                .getString(R.string.theme)
        ) as SwitchPreference?

        // Set listener on switch preference dark mode
        onOffDarkMode!!.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, _ ->
                if (onOffDarkMode.isChecked) {
                    // Deactivate Dark Mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    onOffDarkMode.isChecked = false
                    mActivity?.recreate()
                } else {
                    // Activate Dark Mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    onOffDarkMode.isChecked = true
                    mActivity!!.recreate()
                }
                false
            }
    }
}