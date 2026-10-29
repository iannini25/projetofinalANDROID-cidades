package com.bernardojose.cidades.userinterface

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bernardojose.cidades.viewmodel.CidadesViewModel

@Composable
fun TelaPerfil(vm: CidadesViewModel, onSair: () -> Unit) {
    val favoritas by vm.favoritas.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Perfil", fontSize = 24.sp)
        Text("Cidades salvas: " + favoritas.size)
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                vm.sair()
                onSair()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sair")
        }
    }
}
