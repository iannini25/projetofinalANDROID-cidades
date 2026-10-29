package com.bernardojose.cidades.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Usuario::class, Favorita::class], version = 1)
abstract class BancoDados : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun favoritaDao(): FavoritaDao

    companion object {
        private var instancia: BancoDados? = null

        fun pegarBanco(context: Context): BancoDados {
            if (instancia == null) {
                instancia = Room.databaseBuilder(
                    context.applicationContext,
                    BancoDados::class.java,
                    "cidades.db"
                ).build()
            }
            return instancia!!
        }
    }
}
