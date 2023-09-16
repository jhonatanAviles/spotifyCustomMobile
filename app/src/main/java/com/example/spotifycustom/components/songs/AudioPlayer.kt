package com.example.spotifycustom.components.songs

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.spotifycustom.domain.model.DomainSong
import com.example.spotifycustom.utils.formatSecondsToMinutesAndSeconds
import com.example.spotifycustom.viewmodels.audio.AudioPlayerViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

@Composable
fun AudioPlayer(player: ExoPlayer, songToReproduce: MutableState<DomainSong>) {
    val viewModel: AudioPlayerViewModel = viewModel()

      val imageUrl = "https://d2yoo3qu6vrk5d.cloudfront.net/images/20230228094220/perro-japon.jpg"

    val coroutineScope = rememberCoroutineScope()

    val seekPercentage by viewModel.getCurrentProgressState(songToReproduce.value.songUrl)


    // Prepare the MediaItem with the audio source URL.
    val mediaItem = remember {
        MediaItem.Builder()
            .setUri(Uri.parse(songToReproduce.value.songUrl))
            .build()
    }

    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.secondary)
            .padding(16.dp, 16.dp, 16.dp, 0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image (Assuming you have a function to load the song image)
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null, // Provide a meaningful description if needed
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray), // Placeholder background color
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Play/Stop button
            IconButton(
                onClick = {
                    if (player.isPlaying) {
                        player.pause()
                    } else {
                        player.setMediaItem(mediaItem)
                        player.prepare()
                        player.play()
                    }
                },
                modifier = Modifier.size(40.dp),
            ) {
                Icon(
                    imageVector = if (player.isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (player.isPlaying) "Pause" else "Play",
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Song details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = songToReproduce.value.name,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Duration: ${formatSecondsToMinutesAndSeconds(songToReproduce.value.durationInSeconds)}",
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
            }
        }
        // Seek To Percentage
        Slider(
            value = seekPercentage,
            onValueChange = { newValue ->
                run {
                    viewModel.setSeekPercentage(songToReproduce.value.songUrl, newValue.toString())
                    val seekPercentageLocal =
                        viewModel.getSeekPercentageState(songToReproduce.value.songUrl).value.toFloatOrNull()
                            ?: return@Slider
                    val newPosition = (seekPercentageLocal) * player.duration
                    player.seekTo(newPosition.toLong())
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
    /*
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
                        viewModel.getSeekPercentageState(audioUrl).value.toFloatOrNull()
                            ?: return@Slider
                    val newPosition = (seekPercentageLocal) * player.duration
                    player.seekTo(newPosition.toLong())
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
    }
     */

    // Collect progress updates
    LaunchedEffect(player) {

        while (true) {
            val currentPosition = player.currentPosition
            val duration = player.duration
            val progress = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f
            viewModel.updateProgress(songToReproduce.value.songUrl, progress)
            kotlinx.coroutines.delay(1000)
        }
    }
}