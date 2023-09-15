package com.example.spotifycustom.components.artist

import android.graphics.Color.parseColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.spotifycustom.domain.model.DomainArtist

@Composable
fun ArtistCard(
    artist: DomainArtist,
    colors: Map<String, Map<String, String>>,
    navController: NavController,
    onArtistClick: (String) -> Unit,
) {

    var vibrant by remember { mutableStateOf("#000000") }
    var darkVibrant by remember { mutableStateOf("#000000") }
    var lightVibrant by remember { mutableStateOf("#000000") }
    var domainSwatch by remember { mutableStateOf("#000000") }
    var mutedSwatch by remember { mutableStateOf("#000000") }
    var lightMutedSwatch by remember { mutableStateOf("#000000") }
    var darkMutedSwatch by remember { mutableStateOf("#000000") }
    var onDarkVibrant by remember { mutableStateOf("#ffffff") }

    val brushColor: List<Color> =
        listOf(Color(parseColor(vibrant)).copy(alpha = 0.5f), Color.Transparent)

    // get the colors from the Map
    LaunchedEffect(key1 = true) {
        vibrant = colors[artist.imageUrl]?.get("vibrant")!!
        darkVibrant = colors[artist.imageUrl]?.get("darkVibrant")!!
        lightVibrant = colors[artist.imageUrl]?.get("lightVibrant")!!
        domainSwatch = colors[artist.imageUrl]?.get("domainSwatch")!!
        mutedSwatch = colors[artist.imageUrl]?.get("mutedSwatch")!!
        lightMutedSwatch = colors[artist.imageUrl]?.get("lightMuted")!!
        darkMutedSwatch = colors[artist.imageUrl]?.get("darkMuted")!!
        onDarkVibrant = colors[artist.imageUrl]?.get("onDarkVibrant")!!
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onArtistClick(artist.id)
            },
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)

    ) {
        Column(
            modifier = Modifier
                .background(color = Color(parseColor(darkMutedSwatch))),
        ) {
            Image(
                painter = rememberAsyncImagePainter(artist.imageUrl), // Load the artist's image from a URL
                contentDescription = null, // Provide a meaningful description if needed
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), // Set the image height as needed
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = artist.name,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = artist.genre,
                    style = TextStyle(fontSize = 16.sp, color = Color.White),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
