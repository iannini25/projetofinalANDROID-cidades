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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bernardojose.cidades.viewmodel.CidadesViewModel
import com.bernardojose.cidades.viewmodel.EstadoBusca

@Composable
fun TelaBuscar(vm: CidadesViewModel, onClicarCidade: (Int) -> Unit) {
    var texto by rememberSaveable { mutableStateOf("") }
    val estado by vm.estadoBusca.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Buscar cidades", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Nome da cidade") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { vm.buscar(texto) }, modifier = Modifier.fillMaxWidth()) {
            Text("Buscar")
        }
        Spacer(modifier = Modifier.height(8.dp))

        when (estado) {
            is EstadoBusca.Inicio -> Text("Digite o nome de uma cidade")
            is EstadoBusca.Carregando -> CircularProgressIndicator()
            is EstadoBusca.Erro -> Text((estado as EstadoBusca.Erro).mensagem)
            is EstadoBusca.Sucesso -> {
                val cidades = (estado as EstadoBusca.Sucesso).cidades
                LazyColumn {
                    items(cidades) { cidade ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { onClicarCidade(cidade.id) }
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(cidade.nome, fontSize = 18.sp)
                                Text((cidade.estado ?: "") + " - " + (cidade.pais ?: ""))
                            }
                        }
                    }
                }
            }
        }
    }
}
