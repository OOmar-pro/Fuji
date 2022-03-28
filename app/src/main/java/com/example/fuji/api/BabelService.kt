package com.example.fuji.api

import com.example.fuji.api.models.ChapterPages
import com.example.fuji.api.models.MangaDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BabelService {

    @GET("/list")
    fun listSources(): Call<List<Source>>

    @GET("{id_source}/latests/")
    fun latests(@Path(value = "id_source", encoded = true) id_source: String): Call<List<Manga>>

    @GET("{id_source}/{manga_slug}/")
    fun detail(@Path(value = "id_source", encoded = true) id_source: String, @Path(value = "manga_slug", encoded = true) manga_slug: String): Call<MangaDetail>

    @GET("{id_source}/{manga_slug}/{chapter_number}")
    fun getChapter(@Path(value = "id_source", encoded = true) id_source: String, @Path(value = "manga_slug", encoded = true) manga_slug: String, @Path(value = "chapter_number", encoded = true) chapter_number: String): Call<ChapterPages>

    @GET("{id_source}/{manga_slug}/image/")
    fun getImage(@Path(value = "id_source", encoded = true) id_source: String, @Path(value = "manga_slug", encoded = true) manga_slug: String): Call<String>
}