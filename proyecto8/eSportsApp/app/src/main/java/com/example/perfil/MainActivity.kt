package com.example.perfil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.perfil.ui.theme.PerfilTheme

data class Jugador(
    val id: Int,
    val nombre: String,
    val nickname: String,
    val rol: String,
    @DrawableRes val iconoRes: Int
)

fun jugadoresIniciales(): List<Jugador> = listOf(
    Jugador(1, "Helena Ruiz", "LinkZZ", "Guerrero",  R.drawable.f64_rub),
    Jugador(2, "Carla Gómez", "CharlyX", "Mago",   R.drawable.f26_mor),
    Jugador(3, "Marc Pérez", "dragonblack", "Defensor",  R.drawable.f26_gaf),
    Jugador(4, "Laura Martín", "calamity_hero", "Arquero",  R.drawable.f64_rub),
    Jugador(5, "Paula Torres", "Falcon", "Guerrero",  R.drawable.f26_mor)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PerfilTheme {
                PantallaPrincipal()
            }
        }
    }
}

@Composable
fun PantallaPrincipal() {

    val jugadores = remember {
        mutableStateListOf<Jugador>().apply { addAll(jugadoresIniciales()) }
    }

    var siguienteId by remember { mutableIntStateOf(jugadoresIniciales().size + 1) }

    var idAEliminar by rememberSaveable { mutableStateOf<Int?>(null) }
    var idAEditar by rememberSaveable { mutableStateOf<Int?>(null) }

    val jugadorAEliminar = jugadores.find { it.id == idAEliminar }
    val jugadorAEditar = jugadores.find { it.id == idAEditar }

    if (jugadorAEliminar != null) {
        AlertDialog(
            onDismissRequest = { idAEliminar = null },
            title = { Text("Eliminar jugador") },
            text = { Text("¿Estás seguro de que deseas eliminar a ${jugadorAEliminar.nickname}?") },
            confirmButton = {
                Button(
                    onClick = {
                        jugadores.removeAll { it.id == jugadorAEliminar.id }
                        idAEliminar = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { idAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (jugadorAEditar != null) {
        DialogoEditarJugador(
            jugador = jugadorAEditar,
            onGuardar = { nombre, nickname, rol ->
                val indice = jugadores.indexOfFirst { it.id == jugadorAEditar.id }
                if (indice != -1) {
                    jugadores[indice] = jugadores[indice].copy(
                        nombre = nombre,
                        nickname = nickname,
                        rol = rol
                    )
                }
                idAEditar = null
            },
            onCancelar = { idAEditar = null }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "TEAM TITANS",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(vertical = 20.dp)
                .align(Alignment.CenterHorizontally)
        )

        FormularioJugador(
            onAnadir = { nombre, nickname, rol ->
                jugadores.add(
                    Jugador(
                        id = siguienteId,
                        nombre = nombre,
                        nickname = nickname,
                        rol = rol,
                        iconoRes = R.drawable.f26_mor
                    )
                )
                siguienteId++
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Plantilla actual (${jugadores.size} jugadores)", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)

        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn (
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = jugadores, key = { it.id }) { jugador ->
                TarjetaJugador(jugador = jugador,
                    onEditar = { idAEditar = jugador.id },
                    onEliminar = { idAEliminar = jugador.id } )
            }
        }
    }
}


@Composable
fun DialogoEditarJugador(
    jugador: Jugador,
    onGuardar: (nombre: String, nickname: String, rol: String) -> Unit,
    onCancelar: () -> Unit
) {
    var nombre by rememberSaveable { mutableStateOf(jugador.nombre) }
    var nickname by rememberSaveable { mutableStateOf(jugador.nickname) }
    var rol by rememberSaveable { mutableStateOf(jugador.rol) }
    var error by rememberSaveable { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text("Editar jugador") },
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it; error = null },
                    label = { Text("Nombre") },
                    isError = error != null && nombre.isBlank(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = nickname,
                    onValueChange = { nickname = it; error = null },
                    label = { Text("Nickname") },
                    isError = error != null && nickname.isBlank(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = rol,
                    onValueChange = { rol = it; error = null },
                    label = { Text("Rol") },
                    isError = error != null && rol.isBlank(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                error?.let { mensaje ->
                    Text(text = mensaje, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 4.dp))
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (nombre.isBlank() || nickname.isBlank() || rol.isBlank()) {
                        error = "Todos los campos son obligatorios"
                    } else {
                        onGuardar(nombre.trim(), nickname.trim(), rol.trim())
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun TarjetaJugador(
    jugador: Jugador,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(16.dp))
        .padding(16.dp),) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box {
                Image(
                    painter = painterResource(id = jugador.iconoRes),
                    contentDescription = "Foto de ${jugador.nickname}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Image(
                    painter = painterResource(id = R.drawable.descarga),
                    contentDescription = "Icono",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(22.dp)
                        .clip(CircleShape)
                )
            }

            Column(modifier = Modifier.padding(start = 26.dp)) {
                Text(
                    text = jugador.nickname,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary                )
                Text(
                    text = jugador.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    text = jugador.rol,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally)
        ) {
            OutlinedButton(
                onClick = onEditar,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary)
            ) {
                Text("Editar")
            }
            Button(
                onClick = onEliminar,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text("Eliminar")
            }
        }
    }
}
@Composable
fun FormularioJugador(
    onAnadir: (nombre: String, nickname: String, rol: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var nickname by rememberSaveable { mutableStateOf("") }
    var rol by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf<String?>(null) }

    val coloresCampo = OutlinedTextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onSecondary,
        unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
        focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
        focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
    )

    Column(modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(16.dp))
        .padding(16.dp)) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it; error = null },
            label = { Text("Nombre") },
            isError = error != null && nombre.isBlank(),
            modifier = Modifier.fillMaxWidth(),
            colors = coloresCampo,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it; error = null },
            label = { Text("Nickname") },
            isError = error != null && nickname.isBlank(),
            modifier = Modifier.fillMaxWidth(),
            colors = coloresCampo,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = rol,
            onValueChange = { rol = it; error = null },
            label = { Text("Rol") },
            isError = error != null && rol.isBlank(),
            modifier = Modifier.fillMaxWidth(),
            colors = coloresCampo,
            singleLine = true
        )

        error?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (nombre.isBlank() || nickname.isBlank() || rol.isBlank()) {
                    error = "Todos los campos son obligatorios"
                } else {
                    onAnadir(nombre.trim(), nickname.trim(), rol.trim())
                    nombre = ""
                    nickname = ""
                    rol = ""
                    error = null
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Text("Añadir jugador")
        }
    }
}

@Preview(showBackground = true,
    //uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PantallaPrincipalPreview() {
    PerfilTheme {
        PantallaPrincipal()
    }
}
