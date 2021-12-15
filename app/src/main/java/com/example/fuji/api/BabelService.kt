package com.example.fuji.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BabelService {

    @GET("/list")
    fun listSources(): Call<List<Source>>

    @GET("{id_source}/latests/")
    fun latests(@Path(value = "id_source", encoded = true) id_source: String): Call<List<Manga>>
}