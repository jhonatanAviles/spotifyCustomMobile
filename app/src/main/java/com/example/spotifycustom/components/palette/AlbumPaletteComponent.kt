package com.example.spotifycustom.components.palette

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.spotifycustom.components.album.AlbumCard
import com.example.spotifycustom.domain.model.DomainAlbum
import com.example.spotifycustom.utils.PaletteGenerator
import com.example.spotifycustom.viewmodels.PaletteViewModel

@Composable
fun AlbumPaletteComponent(
    album: DomainAlbum,
    navController: NavController,
    paletteViewModel: PaletteViewModel,
    onAlbumClick: (String) -> Unit
) {

    val colorPalettes by paletteViewModel.colorPalettes
    val context = LocalContext.current

    var launchedEffectTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {

        try {
            val bitmap = PaletteGenerator.convertImageUrlToBitmap(
                imageUrl = album.imageUrl,
                context = context
            )

            if (bitmap != null) {
                launchedEffectTriggered = true
                paletteViewModel.setColorPalette(
                    imageUrl = album.imageUrl,
                    colors = PaletteGenerator.extractColorsFromBitmap(
                        bitmap = bitmap
                    )
                )
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    if (colorPalettes[album.imageUrl]?.isNotEmpty() == true && launchedEffectTriggered) {
        AlbumCard(
            album = album,
            colors = colorPalettes,
            navController = navController,
            onAlbumClick = onAlbumClick
        )
    }
}