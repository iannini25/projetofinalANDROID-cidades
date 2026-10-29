package com.bernardojose.cidades.userinterface

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bernardojose.cidades.viewmodel.CidadesViewModel
import com.bernardojose.cidades.viewmodel.EstadoDetalhe

@Composable
fun TelaDetalhe(vm: CidadesViewModel, cidadeId: Int, onVoltar: () -> Unit) {
    LaunchedEffect(cidadeId) {
        vm.carregarDetalhe(cidadeId)
    }
    val estado by vm.estadoDetalhe.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        IconButton(onClick = { onVoltar() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
        }

        when (estado) {
            is EstadoDetalhe.Carregando -> CircularProgressIndicator()
            is EstadoDetalhe.Erro -> Text((estado as EstadoDetalhe.Erro).mensagem)
            is EstadoDetalhe.Sucesso -> {
                val cidade = (estado as EstadoDetalhe.Sucesso).cidade
                val favorita = (estado as EstadoDetalhe.Sucesso).favorita

                Text(cidade.nome, fontSize = 28.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Estado: " + (cidade.estado ?: "-"))
                Text("Pais: " + (cidade.pais ?: "-"))
                Text("Populacao: " + (cidade.populacao ?: "nao informado"))
                Text("Latitude: " + cidade.latitude)
                Text("Longitude: " + cidade.longitude)
                Text("Altitude: " + (cidade.altitude ?: "-") + " m")
                Text("Fuso horario: " + (cidade.fusoHorario ?: "-"))
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { vm.trocarFavorita(cidade, favorita) }, modifier = Modifier.fillMaxWidth()) {
                    if (favorita) {
                        Text("Remover das favoritas")
                    } else {
                        Text("Salvar nas favoritas")
                    }
                }
            }
        }
    }
}
