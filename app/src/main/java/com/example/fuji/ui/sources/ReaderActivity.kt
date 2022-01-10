package com.example.fuji.ui.sources

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.fuji.R
import com.example.fuji.api.Babel


class ReaderActivity : AppCompatActivity() {

    // API
    private val api: Babel = Babel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reader)

        // get the value passed
        val source_id = intent.getStringExtra("source_id")
        val manga_slug = intent.getStringExtra("manga_slug")
        val chapter_title = intent.getStringExtra("chapter_title")
        val chapter_number = intent.getStringExtra("chapter_number")

        // Set title of activity in Action Bar
        title = chapter_title

        // Show back arrow to return back
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        // get et set list of manga of sources selected
        api.getChapter(findViewById(R.id.list_pages), applicationContext, source_id!!, manga_slug!!, chapter_number!!)
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