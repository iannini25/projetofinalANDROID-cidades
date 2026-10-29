package com.bernardojose.cidades.userinterface

import kotlinx.serialization.Serializable

@Serializable
sealed interface Rota {

    @Serializable
    object Login : Rota

    @Serializable
    object Cadastro : Rota

    @Serializable
    object Painel : Rota

    // abas
    @Serializable
    object Buscar : Rota

    @Serializable
    object Favoritas : Rota

    @Serializable
    object Perfil : Rota

    @Serializable
    data class Detalhe(val cidadeId: Int) : Rota
}
