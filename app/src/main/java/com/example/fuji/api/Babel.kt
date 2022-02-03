package com.example.fuji.api

import android.content.Context
import android.util.Log
import android.widget.*
import androidx.room.Room
import com.example.fuji.R
import com.example.fuji.api.models.ChapterDetail
import com.example.fuji.api.models.ChapterPages
import com.example.fuji.api.models.MangaDetail
import com.example.fuji.api.models.MangaMetadata
import com.example.fuji.database.AppDatabase
import com.example.fuji.database.SourcesEntity
import com.example.fuji.ui.sources.CustomAdapterChapter
import com.example.fuji.ui.sources.CustomAdapterManga
import com.example.fuji.ui.sources.CustomAdapterPages
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Callable

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
                        list.adapter = CustomAdapterManga(mContext, id_source, arrManga)
                    }
                }
            }
            override fun onFailure(call: Call<List<Manga>>, t: Throwable) {
                Toast.makeText(mContext, "Erreur: Impossible de récupérer les mangas disponibles sur la source", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Récupere les details d'un manga pour une source donnee
    fun detail(details_views : MutableList<Any>,list: ListView, mContext: Context, id_source: String, manga_slug: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retrofit.create(BabelService::class.java)
        val detailRequest = service.detail(id_source, manga_slug)

        detailRequest.enqueue(object : Callback<MangaDetail> {
            override fun onResponse(call: Call<MangaDetail>, response: Response<MangaDetail>) {
                val manga_detail = response.body()

                if (manga_detail != null) {
                    // details du manga
                    val metadata : MangaMetadata = manga_detail.metadata!!

                    val image_view : ImageView = details_views.get(0) as ImageView
                    val title_view : TextView = details_views.get(1) as TextView
                    val description_view : TextView = details_views.get(2) as TextView

                    Picasso.get().load(metadata.img).error(R.drawable.white_image).placeholder( R.drawable.progress_animation ).into(image_view)
                    title_view.text = metadata.title
                    description_view.text = metadata.description


                    // Remplissage des chapitres dans la vue
                    val chapters: ArrayList<ChapterDetail> = ArrayList(manga_detail.chapters)
                    list.adapter = CustomAdapterChapter(mContext, id_source, manga_slug, chapters)
                }
            }
            override fun onFailure(call: Call<MangaDetail>, t: Throwable) {
                Log.e("DEV", t.toString());
                Toast.makeText(mContext, "Erreur: Impossible de récupérer les informations du manga.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Récupere les pages d'un chapitre pour un manga et une source donnee
    fun getChapter(list: ListView, mContext: Context, id_source: String, manga_slug: String, chapter_number: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retrofit.create(BabelService::class.java)
        Log.d("DEV_KAGE", id_source)
        Log.d("DEV_KAGE", manga_slug)
        Log.d("DEV_KAGE", chapter_number)

        val detailRequest = service.getChapter(id_source, manga_slug, chapter_number)

        detailRequest.enqueue(object : Callback<ChapterPages> {
            override fun onResponse(call: Call<ChapterPages>, response: Response<ChapterPages>) {
                val chapter = response.body()
                Log.d("DEV_KAGE", chapter.toString())

                if (chapter != null) {
                    // Remplissage des chapitres dans la vue
                    val pages: ArrayList<String> = ArrayList(chapter.pages)
                    list.adapter = CustomAdapterPages(mContext, id_source, manga_slug, pages)
                }
            }
            override fun onFailure(call: Call<ChapterPages>, t: Throwable) {
                Log.e("DEV_KAGE", t.toString());
                Toast.makeText(mContext, "Erreur: Impossible de récupérer les pages du chapitre.", Toast.LENGTH_SHORT).show()
            }
        })
    }

}