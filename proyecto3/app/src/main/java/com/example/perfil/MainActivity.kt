package com.example.perfil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.perfil.ui.theme.PerfilTheme

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

// Pantalla principal: título + 4 jugadores
@Composable
fun PantallaPrincipal() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        Text(
            text = "TEAM TITANS",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(vertical = 20.dp)
                .align(Alignment.CenterHorizontally)
        )

        Jugador(
            nombre = "LinkZZ",
            rol = "Guerrero",
            edad = 29,
            nivel = 77,
            iconoRes = R.drawable.f32_mor
        )
        SeparadorJugadores()

        Jugador(
            nombre = "CharlyX",
            rol = "Mago",
            edad = 42,
            nivel = 66,
            iconoRes = R.drawable.f64_rub
        )
        SeparadorJugadores()

        Jugador(
            nombre = "dragonblack",
            rol = "Defensor",
            edad = 19,
            nivel = 90,
            iconoRes = R.drawable.f26_gaf
        )
        SeparadorJugadores()

        Jugador(
            nombre = "calamity_hero",
            rol = "Arquero",
            edad = 30,
            nivel = 93,
            iconoRes = R.drawable.f26_mor
        )
    }
}

@Composable
fun SeparadorJugadores() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 12.dp),
        thickness = 2.dp,
        color = Color.Gray
    )
}

// Un jugador: foto a la izquierda (Image) y datos a la derecha (Column de Text)
@Composable
fun Jugador(
    nombre: String,
    rol: String,
    edad: Int,
    nivel: Int,
    @DrawableRes iconoRes: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .padding(28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Foto + iconito superpuesto
        Box {
            Image(
                painter = painterResource(id = iconoRes),
                contentDescription = "Foto de $nombre",
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
            Text(text = nombre, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.background)
            Text(text = rol, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimary)
            Text(text = "$edad años", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimary)
            Text(text = "Nivel $nivel", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview() {
    PerfilTheme {
        PantallaPrincipal()
    }
}
