package com.verindrarizya.attendancefirebase.ui.screens.dashboard.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
    onSelectOffice: (Office) -> Unit,
    onIconNotificationClick: () -> Unit,
    onButtonAttendanceClicked: () -> Unit,
) {
    val lazyListState = rememberLazyListState()

    if (homeUiState.isLoading) {
        LoadingDialog()
    }

    Scaffold(
        modifier = modifier,
        backgroundColor = BackgroundScaffoldColor
    ) { paddingValues: PaddingValues ->
        Log.d("HomeTag", paddingValues.toString())
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
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
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Profile",
                                    color = Whiteish,
                                    fontWeight = FontWeight.SemiBold
                                )
                            },
                            actions = {
                                IconButton(onClick = onIconNotificationClick) {
                                    Icon(
                                        Icons.Filled.Notifications,
                                        contentDescription = "Notification",
                                        tint = Whiteish
                                    )
                                }
                            },
                            backgroundColor = Color.Transparent,
                            elevation = 0.dp
                        )
                        Spacer(Modifier.height(20.dp))
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
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
                            backgroundColor = Whiteish
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
    val offices = (1..10).map {
        Office(
            id = it,
            address = "Address $it",
            imageUrl = "asdjasnd",
            name = "Office #$it"
        )
    }
    AttendanceFirebaseTheme {
        HomeScreen(
            onIconNotificationClick = { },
            onButtonAttendanceClicked = { },
            homeUiState = HomeUiState.CheckInUiState(
                isLoading = false,
                listOfOfficeResourceState = ResourceState.Success(offices)
            ),
            onSelectOffice = {}
        )
    }
}

