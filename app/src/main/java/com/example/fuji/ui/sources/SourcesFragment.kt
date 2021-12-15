package com.example.fuji.ui.sources

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.fuji.R
import com.example.fuji.api.Babel
import com.example.fuji.database.AppDatabase
import com.example.fuji.databinding.FragmentSourcesBinding
import kotlinx.coroutines.*
import java.lang.Runnable

import com.example.fuji.api.Source

class SourcesFragment : Fragment() {

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

        // Custom Menu
        setHasOptionsMenu(true);

        _binding = FragmentSourcesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Get Database
        val db = Room.databaseBuilder(
            mContext!!,
            AppDatabase::class.java, "sources.db"
        ).build()

        GlobalScope.launch {
            var data = db.sourcesDao().getAll()

            // if isEmpty call API for get list of sources
            if (data.isEmpty()) {
                api.list(mContext!!)
            }

            val arrSources: ArrayList<Source> = ArrayList()
            for (c in data) {
                arrSources.add(
                    Source(
                        c.id,
                        c.version,
                        c.name,
                        c.img,
                        c.url,
                        c.url_latests,
                        c.url_manga,
                        c.url_chapter,
                        c.url_search,
                        c.active
                    )
                )
            }

            val listView: ListView = binding.listSources

            // Update UI on Main Thread
            mActivity!!.runOnUiThread(Runnable {
                listView.adapter = CustomAdapterSource(mContext!!, arrSources)
            })
        }

        // add listener on each item of listview
        binding.listSources.onItemClickListener = OnItemClickListener { list, v, pos, id ->
            // create intent with SourceInfoActivity
            val intent : Intent =  Intent(mActivity, SourceInfoActivity::class.java)
            // pass in extra name of source of the item selected
            intent.putExtra("id", v.findViewById<TextView>(R.id.idSource).text)
            intent.putExtra("name", v.findViewById<TextView>(R.id.nameSource).text)
            startActivity(intent)
        }

        return root
    }

    // custom menu view
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sources_menu, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.reload_sources) {
            // Get database
            val db = Room.databaseBuilder(
                mContext!!,
                AppDatabase::class.java, "sources.db"
            ).build()

            GlobalScope.launch {
                var data = db.sourcesDao().getAll()

                data?.forEach {
                    db.sourcesDao().delete(it)
                }
                // call API for get list of sources
                //api.list(mContext!!)
            }

            mActivity?.recreate()
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}