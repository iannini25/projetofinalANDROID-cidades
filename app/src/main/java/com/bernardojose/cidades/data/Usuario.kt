package com.bernardojose.cidades.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios", indices = [Index(value = ["email"], unique = true)])
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String,
    // aqui fica so o hash, nunca a senha
    val senhaHash: String
)
