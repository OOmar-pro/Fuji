package com.example.fuji.ui.sources

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.fuji.R
import com.example.fuji.api.Babel


class SourceListMangaActivity : AppCompatActivity() {

    // API
    private val api: Babel = Babel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source_list_manga)

        // get the value passed
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")

        // Set title of activity in Action Bar
        title = name

        // Show back arrow to return back
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        // get et set list of manga of sources selected
        api.latests(findViewById(R.id.list_mangas), applicationContext, id!!)
    }

    // custom menu view
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.source_list_manga_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.search -> {
                Toast.makeText(applicationContext, "TODO : Search - METHOD TO IMPLEMENTED", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}