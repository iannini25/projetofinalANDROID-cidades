package com.bernardojose.cidades.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {

    @Insert
    suspend fun inserir(usuario: Usuario): Long

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun buscarPorEmail(email: String): Usuario?
}
