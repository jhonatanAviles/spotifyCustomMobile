package com.example.spotifycustom.components.artist

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spotifycustom.components.palette.ArtistPaletteComponent
import com.example.spotifycustom.data.repository.MyRepository
import com.example.spotifycustom.navigation.NavScreen
import com.example.spotifycustom.viewmodels.ArtistsViewModel
import com.example.spotifycustom.viewmodels.ArtistsViewModelFactory
import com.example.spotifycustom.viewmodels.PaletteViewModel

@Composable
fun ArtistView(navController: NavController, paletteViewModel: PaletteViewModel) {

    val artistsViewModel: ArtistsViewModel = viewModel(
        factory = ArtistsViewModelFactory(MyRepository())
    )

    val artistsState = artistsViewModel.artists.collectAsState(initial = emptyList())
    val artists = artistsState.value // Access the value of the State

    val onArtistClick = navigateRelatedAlbums(navController)

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 180.dp)
    ) {
        items(artists) { artist ->
            ArtistPaletteComponent(
                artist = artist,
                navController = navController,
                paletteViewModel = paletteViewModel,
                onArtistClick = onArtistClick
            )
        }
    }
}

fun navigateRelatedAlbums(navController: NavController): (String) -> Unit {
    return { item ->
        navController.navigate(NavScreen.ScreenAlbums.route + "?artistId=${item}") {
            launchSingleTop = true
        }
    }
}