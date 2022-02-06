package com.example.fuji.ui.sources

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import com.example.fuji.R
import com.example.fuji.api.Babel
import com.example.fuji.database.AppDatabase
import com.example.fuji.database.MangaEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MangaDetailsActivity : AppCompatActivity() {

    // API
    private val api: Babel = Babel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_manga)

        // get the value passed
        val source_id = intent.getStringExtra("source_id")
        val manga_slug = intent.getStringExtra("manga_slug")
        val manga_title = intent.getStringExtra("manga_title")

        // Set title of activity in Action Bar
        title = manga_title

        // Show back arrow to return back
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        // Bind on button "add to library"
        val add_to_library : Button = findViewById(R.id.save_manga_detail) as Button
        val context = add_to_library.context

        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mangas.db"
        ).build()

        GlobalScope.launch {
            val mangaInLibrary = db.MangaDao().findById(manga_slug!!)

            if (mangaInLibrary != null) {
                add_to_library.setText(R.string.dans_la_biblioth_que)
                add_to_library.setBackgroundResource(R.color.green)

                add_to_library.setOnClickListener {
                    // save manga to library

                    GlobalScope.launch {

                        db.MangaDao().delete(mangaInLibrary)
                    }
                    add_to_library.setText(R.string.ajouter_la_biblioth_que)
                    add_to_library.setBackgroundResource(R.color.gray)
                }
            } else {
                add_to_library.setOnClickListener {
                    // save manga to library

                    GlobalScope.launch {

                        db.MangaDao().insertAll(
                            MangaEntity(
                                0,
                                source_id!!,
                                manga_slug!!,
                                manga_title!!,
                                "https://semantic-ui.com/images/wireframe/white-image.png"
                            )
                        )
                    }

                    add_to_library.setText(R.string.dans_la_biblioth_que)
                    add_to_library.setBackgroundResource(R.color.green)
                }
            }
        }

        // get et set list of manga of sources selected
        val details_views = mutableListOf<Any>()
        details_views.add(findViewById(R.id.image_manga_detail))
        details_views.add(findViewById(R.id.titre_manga_detail))
        details_views.add(findViewById(R.id.description_manga_detail))

        api.detail(details_views, findViewById(R.id.list_chapters), applicationContext, source_id!!, manga_slug!!)
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