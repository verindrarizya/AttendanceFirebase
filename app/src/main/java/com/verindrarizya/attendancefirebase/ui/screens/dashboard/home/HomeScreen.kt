package com.verindrarizya.attendancefirebase.ui.screens.dashboard.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.ui.composables.widget.CircleButton
import com.verindrarizya.attendancefirebase.ui.composables.widget.ListItem
import com.verindrarizya.attendancefirebase.ui.composables.widget.LoadingDialog
import com.verindrarizya.attendancefirebase.ui.composables.widget.OfficeImage
import com.verindrarizya.attendancefirebase.ui.screens.model.Office
import com.verindrarizya.attendancefirebase.ui.theme.AttBlue
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.BackgroundScaffoldColor
import com.verindrarizya.attendancefirebase.ui.theme.BgMustard
import com.verindrarizya.attendancefirebase.ui.theme.ButtonBgGreen
import com.verindrarizya.attendancefirebase.ui.theme.TextDarkBlue
import com.verindrarizya.attendancefirebase.ui.theme.Whiteish
import com.verindrarizya.attendancefirebase.util.ResourceState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeUiState by viewModel.uiState.collectAsState()
    val attendanceRecordFlow by viewModel.attendanceRecordFlow.collectAsState(initial = null)
    val context = LocalContext.current

    LaunchedEffect(attendanceRecordFlow) {
        attendanceRecordFlow?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    HomeScreen(
        modifier = modifier,
        homeUiState = homeUiState,
        onSelectOffice = viewModel::selectOffice,
        onIconNotificationClick = {},
        onButtonAttendanceClicked = viewModel::recordAttendance
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
    onSelectOffice: (Office) -> Unit,
    onIconNotificationClick: () -> Unit,
    onButtonAttendanceClicked: () -> Unit,
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

    val topAppBarShadowElevation by animateDpAsState(
        targetValue = if (isScrolled) 8.dp else 0.dp, label = "toolbar elevation"
    )

    Log.d("HomeTag", "isLoading: ${homeUiState.isLoading}")

    if (homeUiState.isLoading) {
        LoadingDialog()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .shadow(
                        elevation = topAppBarShadowElevation
                    )
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
                    IconButton(onClick = onIconNotificationClick) {
                        Icon(
                            Icons.Filled.Notifications,
                            contentDescription = "Notification",
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
            state = lazyListState,
        ) {
            item {
                Box {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(),
                        painter = painterResource(R.drawable.bg_home),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                    Column {
                        Spacer(
                            Modifier.height(paddingValues.calculateTopPadding() + 16.dp)
                        )
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            content = {
                                Box(
                                    modifier = Modifier
                                        .padding(
                                            vertical = 40.dp
                                        )
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircleButton(
                                        onClick = { onButtonAttendanceClicked() },
                                        text = when (homeUiState) {
                                            is HomeUiState.CheckInUiState -> stringResource(R.string.check_in)
                                            is HomeUiState.CheckOutUiState -> stringResource(R.string.check_out)
                                        },
                                        backgroundColor = when (homeUiState) {
                                            is HomeUiState.CheckInUiState -> ButtonBgGreen
                                            is HomeUiState.CheckOutUiState -> BgMustard
                                        }
                                    )
                                }
                            },
                            colors = CardDefaults.cardColors(
                                containerColor = Whiteish
                            ),
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
                        )
                        .fillMaxWidth(),
                    text = stringResource(R.string.location),
                    color = TextDarkBlue,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            when (homeUiState) {
                is HomeUiState.CheckInUiState -> {
                    when (homeUiState.listOfOfficeResourceState) {
                        is ResourceState.Error -> {}
                        ResourceState.Init -> { /* Do Nothing */
                        }

                        ResourceState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        is ResourceState.Success -> {
                            items(homeUiState.listOfOfficeResourceState.data, key = { it.id }) {
                                ListItem(
                                    modifier = Modifier
                                        .padding(
                                            start = 16.dp,
                                            end = 16.dp,
                                            bottom = 10.dp
                                        ),
                                    header = it.name,
                                    headerTextColor = if (it == homeUiState.selectedOffice) {
                                        Whiteish
                                    } else {
                                        TextDarkBlue
                                    },
                                    subHeader = it.address,
                                    subHeaderTextColor = if (it == homeUiState.selectedOffice) {
                                        Whiteish
                                    } else {
                                        TextDarkBlue
                                    },
                                    imageSlot = {
                                        OfficeImage(
                                            imageUrl = it.imageUrl,
                                            imageContentDescription = "${it.name} Office Image"
                                        )
                                    },
                                    backgroundColor = if (it == homeUiState.selectedOffice) {
                                        AttBlue
                                    } else {
                                        Whiteish
                                    },
                                    onClick = { onSelectOffice(it) }
                                )
                            }
                        }
                    }
                }

                is HomeUiState.CheckOutUiState -> {
                    homeUiState.selectedOffice.let {
                        item {
                            ListItem(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 10.dp
                                    ),
                                header = it.name,
                                headerTextColor = Whiteish,
                                subHeader = it.address,
                                subHeaderTextColor = Whiteish,
                                imageSlot = {
                                    OfficeImage(
                                        imageUrl = it.imageUrl,
                                        imageContentDescription = "${it.name} Office Image"
                                    )
                                },
                                backgroundColor = BgMustard
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    AttendanceFirebaseTheme {
        HomeScreen(
            onIconNotificationClick = { },
            onButtonAttendanceClicked = { },
            homeUiState = HomeUiState.CheckInUiState(),
            onSelectOffice = {}
        )
    }
}

