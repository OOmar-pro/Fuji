package com.grossosdevelopopoyos.fuji.ui.settings

import android.os.Bundle
import com.grossosdevelopopoyos.fuji.R
import androidx.navigation.findNavController
import androidx.preference.*

class SettingsFragmentCompat : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        /*val preferenceFragment: Preference? = findPreference("appearance")

        preferenceFragment?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            view?.findNavController()?.navigate(R.id.navigation_sources)
            true
        }*/

    }
}