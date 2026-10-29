package com.bernardojose.cidades.userinterface

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bernardojose.cidades.viewmodel.CidadesViewModel

data class Aba(val nome: String, val rota: Rota, val icone: ImageVector)

@Composable
fun TelaPainel(vm: CidadesViewModel, onSair: () -> Unit) {
    val navAbas = rememberNavController()

    val abas = listOf(
        Aba("Buscar", Rota.Buscar, Icons.Filled.Search),
        Aba("Favoritas", Rota.Favoritas, Icons.Filled.Favorite),
        Aba("Perfil", Rota.Perfil, Icons.Filled.Person)
    )

    val entradaAtual by navAbas.currentBackStackEntryAsState()
    val destinoAtual = entradaAtual?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                for (aba in abas) {
                    NavigationBarItem(
                        selected = destinoAtual?.hierarchy?.any { it.hasRoute(aba.rota::class) } == true,
                        onClick = {
                            navAbas.navigate(aba.rota) {
                                popUpTo(navAbas.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(aba.icone, contentDescription = aba.nome) },
                        label = { Text(aba.nome) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navAbas,
            startDestination = Rota.Buscar,
            modifier = Modifier.padding(padding)
        ) {
            composable<Rota.Buscar> {
                TelaBuscar(vm, onClicarCidade = { id -> navAbas.navigate(Rota.Detalhe(id)) })
            }
            composable<Rota.Favoritas> {
                TelaFavoritas(vm, onClicarCidade = { id -> navAbas.navigate(Rota.Detalhe(id)) })
            }
            composable<Rota.Perfil> {
                TelaPerfil(vm, onSair = onSair)
            }
            composable<Rota.Detalhe> { entrada ->
                val rota = entrada.toRoute<Rota.Detalhe>()
                TelaDetalhe(vm, rota.cidadeId, onVoltar = { navAbas.popBackStack() })
            }
        }
    }
}
