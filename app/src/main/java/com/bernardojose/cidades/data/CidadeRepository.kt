package com.bernardojose.cidades.data

import android.content.Context
import android.util.Patterns
import com.bernardojose.cidades.network.RetrofitInstancia
import kotlinx.coroutines.flow.Flow

class CidadeRepository(context: Context) {

    private val banco = BancoDados.pegarBanco(context)
    private val usuarioDao = banco.usuarioDao()
    private val favoritaDao = banco.favoritaDao()

    // guarda o id de quem ta logado, -1 = ninguem
    private val prefs = context.getSharedPreferences("sessao", Context.MODE_PRIVATE)

    // ---------- login ----------

    fun usuarioLogado(): Int {
        return prefs.getInt("usuarioId", -1)
    }

    // retorna null se deu certo ou a mensagem de erro
    suspend fun cadastrar(email: String, senha: String): String? {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Email invalido"
        }
        if (senha.length < 6) {
            return "A senha precisa ter 6 ou mais caracteres"
        }
        if (usuarioDao.buscarPorEmail(email) != null) {
            return "Esse email ja esta cadastrado"
        }
        val id = usuarioDao.inserir(Usuario(email = email, senhaHash = senha.sha256()))
        prefs.edit().putInt("usuarioId", id.toInt()).apply()
        return null
    }

    suspend fun entrar(email: String, senha: String): Boolean {
        val usuario = usuarioDao.buscarPorEmail(email)
        if (usuario == null || usuario.senhaHash != senha.sha256()) {
            return false
        }
        prefs.edit().putInt("usuarioId", usuario.id).apply()
        return true
    }

    fun sair() {
        prefs.edit().putInt("usuarioId", -1).apply()
    }

    // ---------- api ----------

    suspend fun buscarCidades(nome: String): List<Cidade> {
        val resposta = RetrofitInstancia.api.buscarCidades(nome)
        if (resposta.resultados == null) {
            return emptyList()
        }
        return resposta.resultados
    }

    suspend fun buscarCidade(id: Int): Cidade {
        return RetrofitInstancia.api.buscarCidadePorId(id)
    }

    // ---------- favoritas (room) ----------

    fun favoritas(usuarioId: Int): Flow<List<Favorita>> {
        return favoritaDao.listar(usuarioId)
    }

    suspend fun ehFavorita(usuarioId: Int, cidadeId: Int): Boolean {
        return favoritaDao.contar(usuarioId, cidadeId) > 0
    }

    suspend fun favoritar(usuarioId: Int, cidade: Cidade) {
        favoritaDao.inserir(
            Favorita(
                usuarioId = usuarioId,
                cidadeId = cidade.id,
                nome = cidade.nome,
                pais = cidade.pais ?: ""
            )
        )
    }

    suspend fun desfavoritar(usuarioId: Int, cidadeId: Int) {
        favoritaDao.remover(usuarioId, cidadeId)
    }
}
