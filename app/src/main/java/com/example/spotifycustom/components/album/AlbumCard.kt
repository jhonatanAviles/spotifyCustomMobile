package com.example.spotifycustom.components.album

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
import com.example.spotifycustom.domain.model.DomainAlbum

@Composable
fun AlbumCard(
    album: DomainAlbum,
    colors: Map<String, Map<String, String>>,
    navController: NavController,
    onAlbumClick: (String) -> Unit,
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
        listOf(Color(android.graphics.Color.parseColor(vibrant)).copy(alpha = 0.5f), Color.Transparent)

    // get the colors from the Map
    LaunchedEffect(key1 = true) {
        vibrant = colors[album.imageUrl]?.get("vibrant")!!
        darkVibrant = colors[album.imageUrl]?.get("darkVibrant")!!
        lightVibrant = colors[album.imageUrl]?.get("lightVibrant")!!
        domainSwatch = colors[album.imageUrl]?.get("domainSwatch")!!
        mutedSwatch = colors[album.imageUrl]?.get("mutedSwatch")!!
        lightMutedSwatch = colors[album.imageUrl]?.get("lightMuted")!!
        darkMutedSwatch = colors[album.imageUrl]?.get("darkMuted")!!
        onDarkVibrant = colors[album.imageUrl]?.get("onDarkVibrant")!!
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onAlbumClick(album.id)
            },
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)

    ) {
        Column(
            modifier = Modifier
                .background(color = Color(android.graphics.Color.parseColor(darkMutedSwatch))),
        ) {
            Image(
                painter = rememberAsyncImagePainter(album.imageUrl), // Load the artist's image from a URL
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

                Text(
                    text = album.name,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = album.yearOfPublication,
                    style = TextStyle(fontSize = 16.sp, color = Color.White),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}