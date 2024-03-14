package com.verindrarizya.attendancefirebase.ui.screens.dashboard.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.core.entity.Office
import com.verindrarizya.attendancefirebase.core.util.Resource
import com.verindrarizya.attendancefirebase.ui.composables.widget.AsyncImageListItem
import com.verindrarizya.attendancefirebase.ui.composables.widget.CircleButton
import com.verindrarizya.attendancefirebase.ui.composables.widget.ListItem
import com.verindrarizya.attendancefirebase.ui.composables.widget.LoadingDialog
import com.verindrarizya.attendancefirebase.ui.composables.widget.OfficeImage
import com.verindrarizya.attendancefirebase.ui.theme.AttBlue
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.BackgroundScaffoldColor
import com.verindrarizya.attendancefirebase.ui.theme.BgMustard
import com.verindrarizya.attendancefirebase.ui.theme.ButtonBgGreen
import com.verindrarizya.attendancefirebase.ui.theme.OrangeRedish
import com.verindrarizya.attendancefirebase.ui.theme.TextDarkBlue
import com.verindrarizya.attendancefirebase.ui.theme.TextGray
import com.verindrarizya.attendancefirebase.ui.theme.Whiteish

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeUiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.messageFlow.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    HomeScreen(
        modifier = modifier,
        homeUiState = homeUiState,
        onSelectOffice = viewModel::selectOffice,
        onIconNotificationClick = {},
        onButtonAttendanceClicked = viewModel::recordAttendance,
        onRefresh = viewModel::refresh
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
    onSelectOffice: (Office) -> Unit,
    onIconNotificationClick: () -> Unit,
    onButtonAttendanceClicked: () -> Unit,
    onRefresh: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    val refreshState = rememberPullRefreshState(
        refreshing = homeUiState.isRefreshing,
        onRefresh = onRefresh
    )

    if (homeUiState.isLoading) {
        LoadingDialog()
    }

    Scaffold(
        modifier = modifier,
        backgroundColor = BackgroundScaffoldColor
    ) { paddingValues: PaddingValues ->
        Box(
            modifier = Modifier.pullRefresh(refreshState)
        ) {
            PullRefreshIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(6f),
                refreshing = homeUiState.isRefreshing,
                state = refreshState,
            )

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
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
                if (homeUiState.isError && homeUiState is HomeUiState.CheckInUiState) {
                    item {
                        ErrorLayout()
                    }
                } else {
                    when (homeUiState) {
                        is HomeUiState.CheckInUiState -> {
                            when (homeUiState.listOfOfficeResource) {
                                is Resource.Error -> {
                                    item {
                                        ErrorLayout()
                                    }
                                }

                                Resource.Init -> { /* Do Nothing */
                                }

                                Resource.Loading -> {
                                    item {
                                        Box(
                                            modifier = Modifier.fillMaxWidth(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                }

                                is Resource.Success -> {
                                    items(
                                        homeUiState.listOfOfficeResource.data,
                                        key = { it.id }) {
                                        AsyncImageListItem(
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
                                                TextGray
                                            },
                                            imageUrl = it.imageUrl,
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
                                        headerTextColor = TextDarkBlue,
                                        subHeader = it.address,
                                        subHeaderTextColor = TextDarkBlue,
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
    }
}

@Composable
fun ErrorLayout(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("error.json")
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 0.5f
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier.size(200.dp),
            composition = composition,
            progress = { progress },
            contentScale = ContentScale.FillWidth
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "Error, Please Refresh",
            fontWeight = FontWeight.SemiBold,
            color = OrangeRedish
        )
    }
}

private val dummyOffices = (1..10).map {
    Office(
        id = it,
        address = "Address $it",
        imageUrl = "asdjasnd",
        name = "Office #$it"
    )
}

@Preview
@Composable
fun HomeScreenSuccessCheckInStatePreview() {
    AttendanceFirebaseTheme {
        HomeScreen(
            onIconNotificationClick = { },
            onButtonAttendanceClicked = { },
            homeUiState = HomeUiState.CheckInUiState(
                isLoading = false,
                listOfOfficeResource = Resource.Success(
                    dummyOffices
                )
            ),
            onSelectOffice = {},
            onRefresh = {}
        )
    }
}

@Preview
@Composable
fun HomeScreenSuccessCheckOutStatePreview() {
    AttendanceFirebaseTheme {
        HomeScreen(
            onIconNotificationClick = { },
            onButtonAttendanceClicked = { },
            homeUiState = HomeUiState.CheckOutUiState(
                isLoading = false,
                selectedOffice = dummyOffices[0]
            ),
            onSelectOffice = {},
            onRefresh = {}
        )
    }
}

@Preview
@Composable
fun HomeScreenLoadingCheckInStatePreview() {
    AttendanceFirebaseTheme {
        HomeScreen(
            homeUiState = HomeUiState.CheckInUiState(
                isLoading = true
            ),
            onSelectOffice = {},
            onIconNotificationClick = { },
            onButtonAttendanceClicked = {},
            onRefresh = {}
        )
    }
}

@Preview
@Composable
fun HomeScreenLoadingCheckOutStatePreview() {
    AttendanceFirebaseTheme {
        HomeScreen(
            homeUiState = HomeUiState.CheckOutUiState(
                isLoading = true,
                selectedOffice = dummyOffices[0]
            ),
            onSelectOffice = {},
            onIconNotificationClick = { },
            onButtonAttendanceClicked = { },
            onRefresh = {}
        )
    }
}

@Preview
@Composable
fun HomeScreenErrorCheckInStatePreview() {
    AttendanceFirebaseTheme {
        HomeScreen(
            homeUiState = HomeUiState.CheckInUiState(
                isError = true
            ),
            onSelectOffice = {},
            onIconNotificationClick = {},
            onButtonAttendanceClicked = {},
            onRefresh = {}
        )
    }
}

@Preview
@Composable
fun HomeScreenErrorCheckOutStatePreview() {
    AttendanceFirebaseTheme {
        HomeScreen(
            homeUiState = HomeUiState.CheckOutUiState(
                isError = true,
                selectedOffice = dummyOffices[0]
            ),
            onSelectOffice = {},
            onIconNotificationClick = {},
            onButtonAttendanceClicked = {},
            onRefresh = {}
        )
    }
}

@Preview
@Composable
fun HomeScreenOfficeErrorCheckInUiStatePreview() {
    AttendanceFirebaseTheme {
        HomeScreen(
            homeUiState = HomeUiState.CheckInUiState(
                isLoading = false,
                listOfOfficeResource = Resource.Error(
                    "Error Gan"
                )
            ),
            onIconNotificationClick = {},
            onSelectOffice = {},
            onRefresh = {},
            onButtonAttendanceClicked = {}
        )
    }
}

@Preview
@Composable
fun HomeScreenOfficeErrorCheckOutUiStatePreview() {
    AttendanceFirebaseTheme {
        HomeScreen(
            homeUiState = HomeUiState.CheckOutUiState(
                isLoading = false,
                selectedOffice = dummyOffices[0]
            ),
            onIconNotificationClick = {},
            onSelectOffice = {},
            onRefresh = {},
            onButtonAttendanceClicked = {}
        )
    }
}