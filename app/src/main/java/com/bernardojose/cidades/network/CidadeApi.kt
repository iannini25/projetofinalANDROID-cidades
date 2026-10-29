package com.bernardojose.cidades.network

import com.bernardojose.cidades.data.Cidade
import com.bernardojose.cidades.data.RespostaBusca
import retrofit2.http.GET
import retrofit2.http.Query

interface CidadeApi {

    @GET("search")
    suspend fun buscarCidades(
        @Query("name") nome: String,
        @Query("count") quantidade: Int = 20,
        @Query("language") lingua: String = "pt"
    ): RespostaBusca

    @GET("get")
    suspend fun buscarCidadePorId(
        @Query("id") id: Int,
        @Query("language") lingua: String = "pt"
    ): Cidade
}
