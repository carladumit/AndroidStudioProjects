package com.carladumit.listaparticipantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.carladumit.listaparticipantes.ui.theme.ListaParticipantesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListaParticipantesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ListaParticipantes()
                }
            }
        }
    }
}

data class Participante(
    val nombre: String,
    val alias: String
)

@Composable
fun ListaParticipantes() {
    var nombre by rememberSaveable { mutableStateOf("") }
    var alias by rememberSaveable { mutableStateOf("") }

    val participantes = remember { mutableStateListOf<Participante>() }
    val formularioValido = nombre.isNotBlank() && alias.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp,24.dp)
    ) {
        Text(
            text = "Inscripción al torneo",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = alias,
            onValueChange = { alias = it },
            label = { Text("Alias") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            enabled = formularioValido,
            onClick = {
                if (formularioValido) {
                    val nuevo = Participante(nombre = nombre.trim(), alias = alias.trim())
                    participantes.add(nuevo)
                    nombre = ""
                    alias = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Añadir participante")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Participantes inscritos: ${participantes.size}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(participantes) { participante ->
                TarjetaParticipante(participante)
            }
        }
    }
}

@Composable
fun TarjetaParticipante(participante: Participante) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = participante.nombre,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Alias: ${participante.alias}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ParticipantesPreview() {
    ListaParticipantesTheme {
        ListaParticipantes()
    }
}

