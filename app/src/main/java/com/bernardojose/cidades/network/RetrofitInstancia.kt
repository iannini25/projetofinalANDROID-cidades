package com.bernardojose.cidades.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstancia {
    private const val URL_BASE = "https://geocoding-api.open-meteo.com/v1/"

    val api: CidadeApi by lazy {
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CidadeApi::class.java)
    }
}
