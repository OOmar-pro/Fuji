package com.grossosdevelopopoyos.fuji.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.SwitchPreferenceCompat
import com.grossosdevelopopoyos.fuji.R
import com.grossosdevelopopoyos.fuji.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        val root: View = inflater.inflate(R.layout.fragment_settings, container, false)


        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragmentCompat())
            .commit()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}

