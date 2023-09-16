package com.example.spotifycustom.components.songs

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.spotifycustom.domain.model.DomainSong
import com.example.spotifycustom.utils.formatSecondsToMinutesAndSeconds
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

@Composable
fun SongComponent(
    song: DomainSong,
    onAddToFavoritesClick: () -> Unit,
    player: ExoPlayer,
    songToReproduce: MutableState<DomainSong>
) {

    // Prepare the MediaItem with the audio source URL.
    val mediaItem = remember {
        MediaItem.Builder()
            .setUri(Uri.parse(song.songUrl))
            .build()
    }

    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }

    Row(
        modifier = Modifier
            .background(
                color = if (songToReproduce.value == song) {
                    MaterialTheme.colors.secondary
                } else {
                    MaterialTheme.colors.background
                }
            )
            .clickable {
                if (songToReproduce.value == song) {
                    return@clickable
                }

                songToReproduce.value = song

                if (player.isPlaying) {
                    player.pause()
                }
                player.setMediaItem(mediaItem)
                player.prepare()
                player.play()
            }
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image (Assuming you have a function to load the song image)
        Image(
            painter = rememberAsyncImagePainter(song.imageUrl),
            contentDescription = null, // Provide a meaningful description if needed
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray), // Placeholder background color
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Song details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = song.name,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Duration: ${formatSecondsToMinutesAndSeconds(song.durationInSeconds)}",
                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Heart button (Add to favorites)
        IconButton(
            onClick = { onAddToFavoritesClick() },
            modifier = Modifier.size(40.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = "Add to Favorites",
                tint = Color.Gray
            )
        }
    }
}
