package com.verindrarizya.attendancefirebase.ui.composables.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RunningWithErrors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.verindrarizya.attendancefirebase.ui.theme.AttBlue
import com.verindrarizya.attendancefirebase.ui.theme.BgGray

@Composable
fun OfficeImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    imageContentDescription: String
) {
    SubcomposeAsyncImage(
        modifier = modifier
            .size(50.dp)
            .background(
                color = BgGray,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp)),
        model = imageUrl,
        contentDescription = imageContentDescription,
        loading = {
            CircularProgressIndicator(
                modifier = Modifier.padding(2.dp)
            )
        },
        error = {
            Icon(
                Icons.Filled.RunningWithErrors,
                contentDescription = null,
                tint = AttBlue
            )
        },
        contentScale = ContentScale.Crop
    )
}