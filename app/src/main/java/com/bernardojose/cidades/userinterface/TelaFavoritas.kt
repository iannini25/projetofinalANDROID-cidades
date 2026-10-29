package com.bernardojose.cidades.userinterface

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bernardojose.cidades.viewmodel.CidadesViewModel

@Composable
fun TelaFavoritas(vm: CidadesViewModel, onClicarCidade: (Int) -> Unit) {
    val favoritas by vm.favoritas.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Cidades favoritas", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))

        if (favoritas.isEmpty()) {
            Text("Voce ainda nao salvou nenhuma cidade")
        } else {
            LazyColumn {
                items(favoritas) { favorita ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onClicarCidade(favorita.cidadeId) }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(favorita.nome, fontSize = 18.sp)
                            Text(favorita.pais)
                        }
                    }
                }
            }
        }
    }
}
