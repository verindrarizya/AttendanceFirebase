package com.verindrarizya.attendancefirebase.ui.composables.widget

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RunningWithErrors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.ui.theme.AttBlue
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.BackgroundIconColor
import com.verindrarizya.attendancefirebase.ui.theme.TextDarkBlue
import com.verindrarizya.attendancefirebase.ui.theme.TextGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    header: String,
    headerTextColor: Color = TextDarkBlue,
    subHeader: String? = null,
    subHeaderTextColor: Color = TextGray,
    imageSlot: @Composable () -> Unit,
    backgroundColor: Color = Color.White,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ),
        onClick = onClick,
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
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
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
        ListItem(
            header = "No. Karyawan",
            subHeader = "NIK-1234566489",
            imageSlot = {
                RoundedCornerIcon(
                    painter = painterResource(R.drawable.ic_employee_id)
                )
            }
        )
    }
}

@Preview
@Composable
fun ClickableListItemPreview() {
    AttendanceFirebaseTheme {
        ListItem(
            onClick = {},
            header = "Logout",
            imageSlot = {
                RoundedCornerIcon(
                    painter = painterResource(R.drawable.ic_logout)
                )
            }
        )
    }
}

@Preview
@Composable
fun OfficeListItemPreview() {
    AttendanceFirebaseTheme {
        ListItem(
            onClick = {},
            header = "Rumah",
            subHeader = "Jl. Raya Ciputat No. 1",
            imageSlot = {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = BackgroundIconColor,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp)),
                    model = "https://docs.google.com/uc?id=1hDcLCmq4237R_04vsikb0bOUATQA3rVg",
                    contentDescription = "Office Image",
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
        )
    }
}