package com.example.spotifycustom.components.album

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spotifycustom.components.palette.AlbumPaletteComponent
import com.example.spotifycustom.data.repository.MyRepository
import com.example.spotifycustom.navigation.NavScreen
import com.example.spotifycustom.viewmodels.AlbumsViewModel
import com.example.spotifycustom.viewmodels.AlbumsViewModelFactory
import com.example.spotifycustom.viewmodels.PaletteViewModel

@Composable
fun AlbumView(navController: NavController, artistId: String?, paletteViewModel: PaletteViewModel) {

    val albumsViewModel: AlbumsViewModel = viewModel(
        factory = AlbumsViewModelFactory(MyRepository())
    )

    val albumsState =
        if (artistId == null) albumsViewModel.albums.collectAsState(initial = emptyList())
        else {
            // Use remember to store StateFlow based on albumId
            val stateFlow = remember(artistId) {
                albumsViewModel.albumsByArtist(artistId)
            }
            stateFlow.collectAsState(initial = emptyList())
        }
    val albums = albumsState.value // Access the value of the State

    val onAlbumClick = navigateRelatedSongs(navController)

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 180.dp)
    ) {
        items(albums) { album ->
            AlbumPaletteComponent(
                album = album,
                navController = navController,
                paletteViewModel = paletteViewModel,
                onAlbumClick = onAlbumClick
            )
        }
    }
}

fun navigateRelatedSongs(navController: NavController): (String) -> Unit {
    return { item ->
        navController.navigate(NavScreen.ScreenSongs.route + "?albumId=${item}") {
            launchSingleTop = true
        }
    }
}