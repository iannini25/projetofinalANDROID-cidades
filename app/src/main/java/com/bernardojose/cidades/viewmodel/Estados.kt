package com.bernardojose.cidades.viewmodel

import com.bernardojose.cidades.data.Cidade

sealed interface EstadoBusca {
    object Inicio : EstadoBusca
    object Carregando : EstadoBusca
    data class Sucesso(val cidades: List<Cidade>) : EstadoBusca
    data class Erro(val mensagem: String) : EstadoBusca
}

sealed interface EstadoDetalhe {
    object Carregando : EstadoDetalhe
    data class Sucesso(val cidade: Cidade, val favorita: Boolean) : EstadoDetalhe
    data class Erro(val mensagem: String) : EstadoDetalhe
}
