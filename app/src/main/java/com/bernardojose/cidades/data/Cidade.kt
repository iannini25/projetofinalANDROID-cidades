package com.bernardojose.cidades.data

import com.google.gson.annotations.SerializedName

// resposta da busca: /search?name=...
data class RespostaBusca(
    @SerializedName("results")
    val resultados: List<Cidade>?
)

data class Cidade(
    val id: Int,
    @SerializedName("name")
    val nome: String,
    @SerializedName("country")
    val pais: String?,
    @SerializedName("admin1")
    val estado: String?,
    val latitude: Double,
    val longitude: Double,
    @SerializedName("population")
    val populacao: Int?,
    @SerializedName("elevation")
    val altitude: Double?,
    @SerializedName("timezone")
    val fusoHorario: String?
)
