package com.bernardojose.cidades.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// cidade salva por um usuario
@Entity(tableName = "favoritas")
data class Favorita(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuarioId: Int,
    val cidadeId: Int,
    val nome: String,
    val pais: String
)
