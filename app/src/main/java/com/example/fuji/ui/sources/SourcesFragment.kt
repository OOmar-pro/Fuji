package com.example.fuji.ui.sources

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fuji.api.Babel
import com.example.fuji.databinding.FragmentSourcesBinding


class SourcesFragment : Fragment() {

    private lateinit var sourcesViewModel: SourcesViewModel
    private var _binding: FragmentSourcesBinding? = null

    // Activity and Context
    private var mContext: Context? = null
    private var mActivity: Activity? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // API
    private val api: Babel = Babel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get the application context
        mContext = this.activity
        mActivity = this.activity

        sourcesViewModel = ViewModelProvider(this)[SourcesViewModel::class.java]

        _binding = FragmentSourcesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Récupère la liste des sources disponible sur l'api et remplit la listview
        api.list(binding, mContext!!)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}