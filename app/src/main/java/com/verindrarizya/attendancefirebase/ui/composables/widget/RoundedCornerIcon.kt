package com.verindrarizya.attendancefirebase.ui.composables.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.verindrarizya.attendancefirebase.ui.theme.BgGray

@Composable
fun RoundedCornerIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    size: Dp = 50.dp,
    backgroundColor: Color = BgGray
) {
    Image(
        modifier = modifier
            .size(size)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp),
        painter = painter,
        contentDescription = "Person",
    )
}