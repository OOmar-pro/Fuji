package com.example.fuji.ui.sources

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.fuji.R
import com.example.fuji.database.AppDatabase
import com.example.fuji.others.DownloadImage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.lang.Exception
import java.net.URL

class SourceinfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sourceinfo)

        // get the value passed
        val name = intent.getStringExtra("name")

        // Set title of activity in Action Bar
        title = name

        // Show back arrow to return back
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        // Get Database
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "sources.db"
        ).build()

        GlobalScope.launch {
            var data = db.sourcesDao().findByName(name!!)

            println(data)
            val image: ImageView = findViewById(R.id.logoSource)
            DownloadImage(image).execute(data.img)

            val name: TextView = findViewById(R.id.nameSourceValue)
            name.text = data.name

            val version: TextView = findViewById(R.id.versionSourceValue)
            version.text = data.version

            val url: TextView = findViewById(R.id.urlSourceValue)
            url.text = data.url

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}