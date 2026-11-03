package com.carladumit.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carladumit.artspaceapp.ui.theme.ArtSpaceAppTheme

data class Artwork(
    @DrawableRes
    val imageRes: Int,
    val title: String,
    val year: String,
    val properties: String
)

val artworks = listOf(
    Artwork(R.drawable.art_1, "Figurine of god Anubis", "ca. 700-332 BC", "Granite"),
    Artwork(R.drawable.art_2, "Bust of the Goddess Sekhmet", "ca. 1390-1352 BC", "Granite"),
    Artwork(R.drawable.art_3, "Statuette of Hathor", "ca. 664-30 BC", "Brass"),
    Artwork(R.drawable.art_4, "Figurine of the Goddess Isis", "ca. 305-30  BC", "Granite"),
    Artwork(R.drawable.art_5, "Goddess Bastet", "ca. 700-300 BC", "Granite"),
    )

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpace()
                }
            }
        }
    }
}

@Composable
fun ArtSpace(modifier: Modifier = Modifier) {
    var current by remember { mutableIntStateOf(0) }
    val artwork = artworks[current]

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        ArtworkWall(
            imageRes = artwork.imageRes,
            description = artwork.title
        )

        Spacer(modifier = Modifier.height(24.dp))

        ArtworkDescriptor(
            title = artwork.title,
            year = artwork.year,
            properties = artwork.properties
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "${current + 1}/${artworks.size}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(34.dp))

        ButtonController(
            onPrevious = { current = (current - 1 + artworks.size) % artworks.size },
            onNext = { current = (current + 1) % artworks.size }
        )
    }
}

@Composable
fun ArtworkWall(@DrawableRes imageRes: Int, description: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shadowElevation = 8.dp,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        color = Color.White
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = description,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .height(380.dp)
        )
    }
}

@Composable
fun ArtworkDescriptor(title: String, year: String, properties: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondary,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "$properties ($year)",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ButtonController(onPrevious: () -> Unit, onNext: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onPrevious,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("< Previous")
        }
        Button(
            onClick = onNext,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Next >")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpace()
}
