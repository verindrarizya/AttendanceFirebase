package com.verindrarizya.attendancefirebase.ui.screens.dashboard.profile

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.ui.composables.widget.ListItem
import com.verindrarizya.attendancefirebase.ui.composables.widget.RoundedCornerIcon
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.BackgroundScaffoldColor
import com.verindrarizya.attendancefirebase.ui.theme.TextDarkBlue
import com.verindrarizya.attendancefirebase.ui.theme.TextGray
import com.verindrarizya.attendancefirebase.ui.theme.Whiteish

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    ProfileScreen(
        modifier = modifier,
        onSignOutClick = viewModel::signOut,
        onChangePasswordClick = {},
        onIconEditClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onSignOutClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onIconEditClick: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    val localDirection = LocalLayoutDirection.current
    val isScrolled by remember {
        derivedStateOf { lazyListState.firstVisibleItemScrollOffset >= 40 }
    }

    val topAppbarContentColorAnimate by animateColorAsState(
        targetValue = if (isScrolled) TextDarkBlue else Whiteish, label = "text toolbar color"
    )

    val topAppBarContainerColorAnimate by animateColorAsState(
        targetValue = if (isScrolled) Whiteish else Color.Transparent, label = "toolbar color"
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .zIndex(6f),
                title = {
                    Text(
                        text = "Profile",
                        color = topAppbarContentColorAnimate,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topAppBarContainerColorAnimate
                ),
                actions = {
                    IconButton(onClick = onIconEditClick) {
                        Icon(
                            painterResource(id = R.drawable.ic_edit),
                            contentDescription = "Edit Profile",
                            tint = topAppbarContentColorAnimate
                        )
                    }
                }
            )
        },
        containerColor = BackgroundScaffoldColor
    ) { paddingValues: PaddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(
                    start = paddingValues.calculateStartPadding(localDirection),
                    end = paddingValues.calculateEndPadding(localDirection),
                    bottom = paddingValues.calculateBottomPadding()
                ),
            state = lazyListState
        ) {
            item {
                Box {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(),
                        painter = painterResource(R.drawable.bg_profile),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                    Column {
                        Spacer(
                            Modifier.height(paddingValues.calculateTopPadding() + 16.dp)
                        )
                        ProfileCard(
                            modifier = Modifier
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 4.dp
                                ),
                            name = "Verindra Rizya",
                            jobTitle = "Android Developer",
                            painter = painterResource(R.drawable.ic_profile)
                        )
                    }
                }
            }
            item {
                Text(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 20.dp,
                            bottom = 14.dp
                        ),
                    text = "GENERAL",
                    color = TextGray,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            item {
                ListItem(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 12.dp
                        ),
                    header = "No. Karyawan",
                    subHeader = "1234567890",
                    imageSlot = {
                        RoundedCornerIcon(
                            painter = painterResource(R.drawable.ic_employee_id)
                        )
                    }
                )
            }
            item {
                ListItem(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 12.dp
                        ),
                    header = "Alamat",
                    subHeader = "Jakarta barat",
                    imageSlot = {
                        RoundedCornerIcon(
                            painter = painterResource(R.drawable.ic_address)
                        )
                    }
                )
            }
            item {
                ListItem(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 12.dp
                        ),
                    header = "Ubah Password",
                    imageSlot = {
                        RoundedCornerIcon(
                            painter = painterResource(R.drawable.ic_lock)
                        )
                    },
                    onClick = onChangePasswordClick
                )
            }
            item {
                ListItem(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 12.dp
                        ),
                    header = "Sign Out",
                    imageSlot = {
                        RoundedCornerIcon(
                            painter = painterResource(R.drawable.ic_logout)
                        )
                    },
                    onClick = onSignOutClick
                )
            }
        }
    }
}

@Composable
fun ProfileCard(
    modifier: Modifier = Modifier,
    name: String,
    jobTitle: String,
    painter: Painter
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Whiteish
        )
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    bottom = 64.dp
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .size(150.dp),
                painter = painter,
                contentDescription = "$name Profile Picture",
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = name,
                color = TextDarkBlue,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = jobTitle,
                fontWeight = FontWeight.Medium,
                color = TextGray
            )
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    AttendanceFirebaseTheme {
        ProfileScreen(
            onSignOutClick = {},
            onChangePasswordClick = {},
            onIconEditClick = {}
        )
    }
}

//@Preview
//@Composable
//fun ProfileCardPreview() {
//    AttendanceFirebaseTheme {
//        ProfileCard(
//            name = "Verindra Rizya",
//            jobTitle = "Android Developer",
//            painter = painterResource(R.drawable.ic_profile)
//        )
//    }
//}