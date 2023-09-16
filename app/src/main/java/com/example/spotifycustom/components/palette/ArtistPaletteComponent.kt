package com.example.spotifycustom.components.palette

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.spotifycustom.components.artist.ArtistCard
import com.example.spotifycustom.domain.model.DomainArtist
import com.example.spotifycustom.utils.PaletteGenerator.convertImageUrlToBitmap
import com.example.spotifycustom.utils.PaletteGenerator.extractColorsFromBitmap
import com.example.spotifycustom.viewmodels.PaletteViewModel

@Composable
fun ArtistPaletteComponent(
    artist: DomainArtist,
    navController: NavController,
    paletteViewModel: PaletteViewModel,
    onArtistClick: (String) -> Unit
) {

    Log.d("RENDERS", "Rendering..." + artist.name)

    val colorPalettes by paletteViewModel.colorPalettes
    val context = LocalContext.current

    var launchedEffectTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        if (colorPalettes.containsKey(artist.imageUrl)) {
            launchedEffectTriggered = true
            return@LaunchedEffect
        }

        try {
            val bitmap = convertImageUrlToBitmap(
                imageUrl = artist.imageUrl,
                context = context
            )

            if (bitmap != null) {
                launchedEffectTriggered = true
                paletteViewModel.setColorPalette(
                    imageUrl = artist.imageUrl,
                    colors = extractColorsFromBitmap(
                        bitmap = bitmap
                    )
                )
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    if (colorPalettes[artist.imageUrl]?.isNotEmpty() == true && launchedEffectTriggered) {
        ArtistCard(
            artist = artist,
            colors = colorPalettes,
            navController = navController,
            onArtistClick = onArtistClick
        )
    }
}