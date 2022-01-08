package com.example.fuji.api

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
}