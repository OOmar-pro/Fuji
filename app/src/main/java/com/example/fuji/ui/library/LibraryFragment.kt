package com.example.fuji.ui.library

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.fuji.R
import com.example.fuji.api.Manga
import com.example.fuji.api.Source
import com.example.fuji.database.AppDatabase
import com.example.fuji.databinding.FragmentLibraryBinding
import com.example.fuji.databinding.FragmentSourcesBinding
import com.example.fuji.ui.sources.CustomAdapterSource
import com.example.fuji.ui.sources.MangaDetailsActivity
import com.example.fuji.ui.sources.SourceInfoActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LibraryFragment : Fragment() {

    private lateinit var libraryViewModel: LibraryViewModel
    private var _binding: FragmentLibraryBinding? = null

    // Activity and Context
    private var mContext: Context? = null
    private var mActivity: Activity? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get the application context
        mContext = this.activity
        mActivity = this.activity

        // Custom Menu
        setHasOptionsMenu(true);

        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Get Database
        val db = Room.databaseBuilder(
            mContext!!,
            AppDatabase::class.java, "mangas.db"
        ).build()

        GlobalScope.launch {
            var data = db.MangaDao().getAll()

            // if isEmpty call API for get list of sources
            if (data.isEmpty()) {
                // afficher library vide
                // TODO
            }

            val arrMangas: ArrayList<Manga> = ArrayList()
            for (c in data) {
                arrMangas.add(
                    Manga(
                        c.manga_title,
                        c.manga_slug,
                        c.source_id,
                        c.manga_img,
                    )
                )
            }

            val listView: ListView = binding.libraryView

            // Update UI on Main Thread
            mActivity!!.runOnUiThread(Runnable {
                listView.adapter = CustomAdapterLibrary(mContext!!, arrMangas)
            })
        }

        // add listener on each item of listview
        binding.libraryView.onItemClickListener =
            AdapterView.OnItemClickListener { list, v, pos, id ->
                val manga : Manga = list.getItemAtPosition(pos) as Manga

                // create intent with SourceInfoActivity
                val intent: Intent = Intent(mActivity, MangaDetailsActivity::class.java)
                // pass in extra name of source of the item selected
                intent.putExtra("source_id", manga.source_id)
                intent.putExtra("manga_slug", manga.slug)
                intent.putExtra("manga_title", manga.title)
                startActivity(intent)
            }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}