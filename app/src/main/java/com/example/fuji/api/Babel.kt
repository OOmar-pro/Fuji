package com.example.fuji.api

import android.content.Context
import android.widget.ListView
import android.widget.Toast
import com.example.fuji.databinding.FragmentSourcesBinding
import com.example.fuji.ui.sources.CustomAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Babel {

    private val baseURL = "http://163.173.118.4:8000/"

    fun list(binding: FragmentSourcesBinding, mContext: Context) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retrofit.create(SourceService::class.java)
        val sourceRequest = service.listSources()

        sourceRequest.enqueue(object : Callback<List<Source>> {
            override fun onResponse(
                call: Call<List<Source>>,
                response: Response<List<Source>>
            ) {
                val allSource = response.body()
                if (allSource != null) {
                    val arrSources: ArrayList<com.example.fuji.ui.sources.Source> = ArrayList()
                    for (c in allSource) {
                        arrSources.add(
                            com.example.fuji.ui.sources.Source(
                                c.img,
                                c.name,
                                "1.0"
                            )
                        )
                        val listView: ListView = binding.listSources
                        listView.adapter = CustomAdapter(mContext!!, arrSources)
                        println(" ${c.name} : ${c.url} : ${c.img} ")

                    }
                }
            }

            override fun onFailure(call: Call<List<Source>>, t: Throwable) {
                Toast.makeText(mContext, "Erreur: Impossible de récupérer les sources disponibles du l'api", Toast.LENGTH_SHORT).show()
            }
        })
    }

}