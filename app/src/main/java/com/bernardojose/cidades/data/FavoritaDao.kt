package com.bernardojose.cidades.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritaDao {

    @Query("SELECT * FROM favoritas WHERE usuarioId = :usuarioId")
    fun listar(usuarioId: Int): Flow<List<Favorita>>

    @Insert
    suspend fun inserir(favorita: Favorita)

    @Query("DELETE FROM favoritas WHERE usuarioId = :usuarioId AND cidadeId = :cidadeId")
    suspend fun remover(usuarioId: Int, cidadeId: Int)

    @Query("SELECT COUNT(*) FROM favoritas WHERE usuarioId = :usuarioId AND cidadeId = :cidadeId")
    suspend fun contar(usuarioId: Int, cidadeId: Int): Int
}
