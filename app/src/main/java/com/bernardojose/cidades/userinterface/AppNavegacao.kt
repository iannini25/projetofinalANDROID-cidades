package com.bernardojose.cidades.userinterface

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bernardojose.cidades.viewmodel.CidadesViewModel

@Composable
fun AppNavegacao() {
    val vm: CidadesViewModel = viewModel()
    val nav = rememberNavController()

    // se ja tem alguem logado abre direto no app
    val inicio: Rota = remember {
        if (vm.estaLogado()) Rota.Painel else Rota.Login
    }

    NavHost(navController = nav, startDestination = inicio) {

        composable<Rota.Login> {
            TelaLogin(
                vm,
                onEntrou = {
                    nav.navigate(Rota.Painel) {
                        popUpTo(Rota.Login) { inclusive = true }
                    }
                },
                onIrCadastro = {
                    vm.limparMensagem()
                    nav.navigate(Rota.Cadastro)
                }
            )
        }

        composable<Rota.Cadastro> {
            TelaCadastro(
                vm,
                onCadastrou = {
                    nav.navigate(Rota.Painel) {
                        popUpTo(Rota.Login) { inclusive = true }
                    }
                },
                onVoltar = {
                    vm.limparMensagem()
                    nav.popBackStack()
                }
            )
        }

        composable<Rota.Painel> {
            TelaPainel(vm, onSair = {
                nav.navigate(Rota.Login) {
                    popUpTo(Rota.Painel) { inclusive = true }
                }
            })
        }
    }
}
