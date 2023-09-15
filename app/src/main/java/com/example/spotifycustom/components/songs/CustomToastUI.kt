package com.example.spotifycustom.components.songs

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spotifycustom.ui.theme.SpotifyCustomTheme
import com.example.spotifycustom.viewmodels.audio.AudioPlayerViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

@Composable
fun AudioPlayer() {
    val viewModel: AudioPlayerViewModel = viewModel()
    val context = LocalContext.current
    val audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"

    val player = remember {
        ExoPlayer.Builder(context).build()
    }

    val coroutineScope = rememberCoroutineScope()

    val currentProgress by viewModel.getCurrentProgressState(audioUrl)
    val seekPercentage by viewModel.getCurrentProgressState(audioUrl)


    // Prepare the MediaItem with the audio source URL.
    val mediaItem = remember {
        MediaItem.Builder()
            .setUri(Uri.parse(audioUrl))
            .build()
    }

    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }

    SpotifyCustomTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Audio Player") },
                )
            }
        ) {padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Play/Pause Button
                Button(
                    onClick = {
                        if (player.isPlaying) {
                            player.pause()
                        } else {
                            player.setMediaItem(mediaItem)
                            player.prepare()
                            player.play()
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(if (player.isPlaying) "Pause" else "Play")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Progress Bar
                LinearProgressIndicator(
                    progress = currentProgress,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Seek To Percentage
                Slider(
                    value = seekPercentage,
                    onValueChange = { newValue ->
                        run {
                            viewModel.setSeekPercentage(audioUrl, newValue.toString())
                            val seekPercentageLocal =
                                viewModel.getSeekPercentageState(audioUrl).value.toFloatOrNull() ?: return@Slider
                            val newPosition = (seekPercentageLocal) * player.duration
                            player.seekTo(newPosition.toLong())
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                )


                Button(
                    onClick = {
                        val seekPercentageLocal =
                            viewModel.getSeekPercentageState(audioUrl).value.toFloatOrNull() ?: return@Button
                        val newPosition = (seekPercentageLocal) * player.duration
                        player.seekTo(newPosition.toLong())
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Seek To Percentage")
                }
            }
        }
    }

    // Collect progress updates
    LaunchedEffect(player) {

        while (true) {
            val currentPosition = player.currentPosition
            val duration = player.duration
            val progress = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f
            viewModel.updateProgress(audioUrl, progress)
            kotlinx.coroutines.delay(1000)
        }
    }
}