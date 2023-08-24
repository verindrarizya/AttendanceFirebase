package com.verindrarizya.attendancefirebase.ui.composables.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RunningWithErrors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageScope
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.ui.theme.AttBlue
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.BgGray
import com.verindrarizya.attendancefirebase.ui.theme.TextDarkBlue
import com.verindrarizya.attendancefirebase.ui.theme.TextGray

@Composable
fun AsyncImageListItem(
    modifier: Modifier = Modifier,
    header: String,
    headerTextColor: Color = TextDarkBlue,
    subHeader: String? = null,
    subHeaderTextColor: Color = TextGray,
    loadingContent: @Composable SubcomposeAsyncImageScope.(AsyncImagePainter.State.Loading) -> Unit = {
        CircularProgressIndicator(
            modifier = Modifier.padding(2.dp)
        )
    },
    errorContent: @Composable SubcomposeAsyncImageScope.(AsyncImagePainter.State.Error) -> Unit = {
        Icon(
            Icons.Filled.RunningWithErrors,
            contentDescription = null,
            tint = AttBlue
        )
    },
    imageUrl: String,
    onClick: () -> Unit,
    backgroundColor: Color = Color.White,
    border: BorderStroke? = null
) {
    ListItem(
        modifier = modifier,
        onClick = onClick,
        header = header,
        headerTextColor = headerTextColor,
        subHeader = subHeader,
        subHeaderTextColor = subHeaderTextColor,
        imageSlot = {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = BgGray,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp)),
                model = imageUrl,
                contentDescription = "Office Image",
                loading = loadingContent,
                error = errorContent,
                contentScale = ContentScale.Crop
            )
        },
        backgroundColor = backgroundColor,
        border = border
    )
}

@Composable
fun AsyncImageListItem(
    modifier: Modifier = Modifier,
    header: String,
    headerTextColor: Color = TextDarkBlue,
    subHeader: String? = null,
    subHeaderTextColor: Color = TextGray,
    loadingContent: @Composable SubcomposeAsyncImageScope.(AsyncImagePainter.State.Loading) -> Unit = {
        CircularProgressIndicator(
            modifier = Modifier.padding(2.dp)
        )
    },
    errorContent: @Composable SubcomposeAsyncImageScope.(AsyncImagePainter.State.Error) -> Unit = {
        Icon(
            Icons.Filled.RunningWithErrors,
            contentDescription = null,
            tint = AttBlue
        )
    },
    imageUrl: String,
    backgroundColor: Color = Color.White,
    border: BorderStroke? = null
) {
    ListItem(
        modifier = modifier,
        header = header,
        headerTextColor = headerTextColor,
        subHeader = subHeader,
        subHeaderTextColor = subHeaderTextColor,
        imageSlot = {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = BgGray,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp)),
                model = imageUrl,
                contentDescription = "Office Image",
                loading = loadingContent,
                error = errorContent,
                contentScale = ContentScale.Crop
            )
        },
        backgroundColor = backgroundColor,
        border = border
    )
}

@Composable
fun IconListItem(
    modifier: Modifier = Modifier,
    header: String,
    headerTextColor: Color = TextDarkBlue,
    subHeader: String? = null,
    subHeaderTextColor: Color = TextGray,
    painter: Painter,
    backgroundColor: Color = Color.White,
    onClick: () -> Unit,
    border: BorderStroke? = null
) {
    ListItem(
        modifier = modifier,
        header = header,
        headerTextColor = headerTextColor,
        subHeader = subHeader,
        subHeaderTextColor = subHeaderTextColor,
        imageSlot = {
            RoundedCornerIcon(painter = painter)
        },
        backgroundColor = backgroundColor,
        onClick = onClick,
        border = border
    )
}

@Composable
fun IconListItem(
    modifier: Modifier = Modifier,
    header: String,
    headerTextColor: Color = TextDarkBlue,
    subHeader: String? = null,
    subHeaderTextColor: Color = TextGray,
    painter: Painter,
    backgroundColor: Color = Color.White,
    border: BorderStroke? = null
) {
    ListItem(
        modifier = modifier,
        header = header,
        headerTextColor = headerTextColor,
        subHeader = subHeader,
        subHeaderTextColor = subHeaderTextColor,
        imageSlot = {
            RoundedCornerIcon(painter = painter)
        },
        backgroundColor = backgroundColor,
        border = border
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    header: String,
    headerTextColor: Color = TextDarkBlue,
    subHeader: String? = null,
    subHeaderTextColor: Color = TextGray,
    imageSlot: @Composable () -> Unit,
    backgroundColor: Color = Color.White,
    onClick: () -> Unit,
    border: BorderStroke? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = backgroundColor,
        border = border
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            imageSlot()
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = header,
                    color = headerTextColor,
                    fontWeight = FontWeight.SemiBold
                )
                if (subHeader != null) {
                    Text(
                        text = subHeader,
                        fontSize = 12.sp,
                        color = subHeaderTextColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    header: String,
    headerTextColor: Color = TextDarkBlue,
    subHeader: String? = null,
    subHeaderTextColor: Color = TextGray,
    imageSlot: @Composable () -> Unit,
    backgroundColor: Color = Color.White,
    border: BorderStroke? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = backgroundColor,
        border = border
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            imageSlot()
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = header,
                    color = headerTextColor,
                    fontWeight = FontWeight.SemiBold
                )
                if (!subHeader.isNullOrBlank()) {
                    Text(
                        text = subHeader,
                        fontSize = 12.sp,
                        color = subHeaderTextColor,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ListItemPreview() {
    AttendanceFirebaseTheme {
        IconListItem(
            header = "No. Karyawan",
            subHeader = "NIK-1234566489",
            painter = painterResource(R.drawable.ic_employee_id)
        )
    }
}

@Preview
@Composable
fun ClickableListItemPreview() {
    AttendanceFirebaseTheme {
        IconListItem(
            header = "Logout",
            painter = painterResource(R.drawable.ic_logout)
        )
    }
}

@Preview
@Composable
fun OfficeListItemPreview() {
    AttendanceFirebaseTheme {
        AsyncImageListItem(
            header = "Kantor Pusat",
            subHeader = "Jalan Cabang 1",
            imageUrl = "https://docs.google.com/uc?id=1hDcLCmq4237R_04vsikb0bOUATQA3rVg"
        )
    }
}