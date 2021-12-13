package com.example.fuji.api

import android.content.Context
import android.content.Intent
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.room.Room
import com.example.fuji.database.AppDatabase
import com.example.fuji.database.SourcesEntity
import com.example.fuji.databinding.FragmentSourcesBinding
import com.example.fuji.ui.sources.CustomAdapter
import com.example.fuji.ui.sources.SourceinfoActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Babel {

    private val baseURL = "https://babel-api7340.herokuapp.com/"

    // Récupere la liste des sources activées et la stocke dans la bdd
    fun list(mContext: Context) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retrofit.create(SourceService::class.java)
        val sourceRequest = service.listSources()

        sourceRequest.enqueue(object : Callback<List<Source>> {
            override fun onResponse(call: Call<List<Source>>, response: Response<List<Source>>) {
                val db = Room.databaseBuilder(
                    mContext!!,
                    AppDatabase::class.java, "sources.db"
                ).build()

                val allSource = response.body()
                if (allSource != null) {
                    GlobalScope.launch {
                        var data = db.sourcesDao().getAll()
                        if (data.isEmpty()) {
                            for (c in allSource) {
                                if (c.active) {
                                    db.sourcesDao().insertAll(SourcesEntity(c.id, c.version, c.name, c.img, c.url, c.url_latests, c.url_manga, c.url_chapter, c.url_search, c.active))
                                }
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Source>>, t: Throwable) {
                Toast.makeText(mContext, "Erreur: Impossible de récupérer les sources disponibles du l'api", Toast.LENGTH_SHORT).show()
            }
        })
    }

}