package com.example.fuji.api

import retrofit2.Call
import retrofit2.http.GET

interface SourceService {

    @GET("/list")
    fun listSources(): Call<List<Source>>
}