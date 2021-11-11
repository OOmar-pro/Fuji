package com.grossosdevelopopoyos.fuji.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.grossosdevelopopoyos.fuji.R
import com.grossosdevelopopoyos.fuji.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {

    private lateinit var libraryViewModel: LibraryViewModel
    private var _binding: FragmentLibraryBinding? = null

    private var pages_tests = mutableListOf(R.drawable.page1, R.drawable.page2, R.drawable.page3, R.drawable.page4, R.drawable.page5, R.drawable.page6, R.drawable.page7, R.drawable.page8, R.drawable.page9);

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        libraryViewModel =
            ViewModelProvider(this).get(LibraryViewModel::class.java)

        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        val root: View = binding.gridBiblio

        val adapter = object : ArrayAdapter(this, android.R.layout.simple_list_item_1, pages_tests) {
            fun getView(
                position: Int, convertView: View?,
                parent: ViewGroup
            ): View {

                return (super.getView(position, convertView, parent) as TextView)
                    .apply {
                        text = pages_tests[position]
                        gravity = Gravity.CENTER

                        // grid view vertical spacing, horizontal spacing and
                        // item background color together make border between items
                        setBackgroundColor(Color.parseColor("#66DDAA"))
                    }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}