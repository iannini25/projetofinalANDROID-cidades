package com.bernardojose.cidades.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bernardojose.cidades.data.Cidade
import com.bernardojose.cidades.data.CidadeRepository
import com.bernardojose.cidades.data.Favorita
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CidadesViewModel(application: Application) : AndroidViewModel(application) {

    private val repositorio = CidadeRepository(application)

    // id de quem ta logado (-1 = ninguem)
    private val usuarioId = MutableStateFlow(repositorio.usuarioLogado())

    // quando troca o usuario troca a lista de favoritas tambem
    @OptIn(ExperimentalCoroutinesApi::class)
    val favoritas: StateFlow<List<Favorita>> = usuarioId
        .flatMapLatest { id -> repositorio.favoritas(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _estadoBusca = MutableStateFlow<EstadoBusca>(EstadoBusca.Inicio)
    val estadoBusca: StateFlow<EstadoBusca> = _estadoBusca

    private val _estadoDetalhe = MutableStateFlow<EstadoDetalhe>(EstadoDetalhe.Carregando)
    val estadoDetalhe: StateFlow<EstadoDetalhe> = _estadoDetalhe

    // mensagem de erro do login / cadastro
    private val _mensagem = MutableStateFlow("")
    val mensagem: StateFlow<String> = _mensagem

    fun estaLogado(): Boolean {
        return usuarioId.value != -1
    }

    fun limparMensagem() {
        _mensagem.value = ""
    }

    fun cadastrar(email: String, senha: String, onSucesso: () -> Unit) {
        viewModelScope.launch {
            val erro = repositorio.cadastrar(email.trim(), senha)
            if (erro == null) {
                usuarioId.value = repositorio.usuarioLogado()
                _mensagem.value = ""
                onSucesso()
            } else {
                _mensagem.value = erro
            }
        }
    }

    fun entrar(email: String, senha: String, onSucesso: () -> Unit) {
        viewModelScope.launch {
            val deuCerto = repositorio.entrar(email.trim(), senha)
            if (deuCerto) {
                usuarioId.value = repositorio.usuarioLogado()
                _mensagem.value = ""
                onSucesso()
            } else {
                // nao fala qual dos dois ta errado
                _mensagem.value = "Email ou senha incorretos"
            }
        }
    }

    fun sair() {
        repositorio.sair()
        usuarioId.value = -1
        _estadoBusca.value = EstadoBusca.Inicio
    }

    fun buscar(nome: String) {
        viewModelScope.launch {
            _estadoBusca.value = EstadoBusca.Carregando
            try {
                val lista = repositorio.buscarCidades(nome)
                if (lista.isEmpty()) {
                    _estadoBusca.value = EstadoBusca.Erro("Nenhuma cidade encontrada")
                } else {
                    _estadoBusca.value = EstadoBusca.Sucesso(lista)
                }
            } catch (e: Exception) {
                _estadoBusca.value = EstadoBusca.Erro("Erro ao buscar, verifique a internet")
            }
        }
    }

    fun carregarDetalhe(cidadeId: Int) {
        viewModelScope.launch {
            _estadoDetalhe.value = EstadoDetalhe.Carregando
            try {
                val cidade = repositorio.buscarCidade(cidadeId)
                val favorita = repositorio.ehFavorita(usuarioId.value, cidadeId)
                _estadoDetalhe.value = EstadoDetalhe.Sucesso(cidade, favorita)
            } catch (e: Exception) {
                _estadoDetalhe.value = EstadoDetalhe.Erro("Erro ao carregar a cidade, verifique a internet")
            }
        }
    }

    fun trocarFavorita(cidade: Cidade, ehFavorita: Boolean) {
        viewModelScope.launch {
            if (ehFavorita) {
                repositorio.desfavoritar(usuarioId.value, cidade.id)
            } else {
                repositorio.favoritar(usuarioId.value, cidade)
            }
            _estadoDetalhe.value = EstadoDetalhe.Sucesso(cidade, !ehFavorita)
        }
    }
}
