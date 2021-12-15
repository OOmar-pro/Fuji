package com.example.fuji.api

import android.content.Context
import android.widget.ListView
import android.widget.Toast
import androidx.room.Room
import com.example.fuji.database.AppDatabase
import com.example.fuji.database.SourcesEntity
import com.example.fuji.ui.sources.CustomAdapterManga
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

        val service = retrofit.create(BabelService::class.java)
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
                                    db.sourcesDao().insertAll(
                                        SourcesEntity(
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
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Source>>, t: Throwable) {
                Toast.makeText(mContext, "Erreur: Impossible de récupérer les sources disponibles sur l'api", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Récupere la liste des derniers mangas sortis d'une source
    fun latests(list: ListView, mContext: Context, id_source: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retrofit.create(BabelService::class.java)
        val latestsRequest = service.latests(id_source)

        latestsRequest.enqueue(object : Callback<List<Manga>> {
            override fun onResponse(call: Call<List<Manga>>, response: Response<List<Manga>>) {
                val latestsMangas = response.body()
                if (latestsMangas != null) {
                    for (c in latestsMangas) {
                        val arrManga: ArrayList<Manga> = ArrayList()
                        for (c in latestsMangas) {
                            arrManga.add(
                                Manga(
                                    c.title,
                                    c.slug,
                                    c.url,
                                    c.img
                                )
                            )
                        }

                        list.adapter = CustomAdapterManga(mContext, arrManga)
                    }
                }
            }
            override fun onFailure(call: Call<List<Manga>>, t: Throwable) {
                Toast.makeText(mContext, "Erreur: Impossible de récupérer les mangas disponibles sur la source", Toast.LENGTH_SHORT).show()
            }
        })
    }

}