package com.verindrarizya.attendancefirebase.ui.screens.dashboard.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.ui.composables.widget.AsyncImageListItem
import com.verindrarizya.attendancefirebase.ui.screens.model.AttendanceRecord
import com.verindrarizya.attendancefirebase.ui.theme.AttBlue
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.BgGray
import com.verindrarizya.attendancefirebase.ui.theme.BgMustard
import com.verindrarizya.attendancefirebase.ui.theme.ButtonBgBlue
import com.verindrarizya.attendancefirebase.ui.theme.TextDarkBlue
import com.verindrarizya.attendancefirebase.ui.theme.TextGray
import com.verindrarizya.attendancefirebase.ui.theme.Whiteish
import com.verindrarizya.attendancefirebase.util.AttendanceState
import com.verindrarizya.attendancefirebase.util.DataDummy
import com.verindrarizya.attendancefirebase.util.ResourceState

private val listOfFilter: List<HistoryDateFilter> = listOf(
    HistoryDateFilter.Day(),
    HistoryDateFilter.Week(),
    HistoryDateFilter.Month(),
    HistoryDateFilter.Year()
)

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val selectedHistoryDateFilter by viewModel.selectedHistoryDateFilter.collectAsStateWithLifecycle()
    val attendanceRecordResourceState by viewModel.attendanceRecordResourceState.collectAsStateWithLifecycle()

    HistoryScreen(
        modifier = modifier,
        selectedHistoryDateFilter = selectedHistoryDateFilter,
        onSelectHistoryDateFilter = { viewModel.selectHistoryDateFilter(it) },
        onRefresh = {},
        attendanceRecordResourceState = attendanceRecordResourceState,
        onIconNotificationClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    historyDateFilters: List<HistoryDateFilter> = listOfFilter,
    selectedHistoryDateFilter: HistoryDateFilter?,
    onSelectHistoryDateFilter: (HistoryDateFilter) -> Unit,
    onIconNotificationClick: () -> Unit,
    onRefresh: () -> Unit,
    attendanceRecordResourceState: ResourceState<List<AttendanceRecord>>,
) {
    val localDirection = LocalLayoutDirection.current

    Scaffold(
        modifier = modifier,
        containerColor = Color.Gray,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .zIndex(6f),
                title = {
                    Text(
                        text = "Attendance History",
                        color = Whiteish,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                actions = {
                    IconButton(onClick = onIconNotificationClick) {
                        Icon(
                            Icons.Filled.Notifications,
                            contentDescription = "Notification",
                            tint = Whiteish
                        )
                    }
                }
            )
        }
    ) { paddingValues: PaddingValues ->
        Box(
            modifier = Modifier
                .padding(
                    start = paddingValues.calculateStartPadding(localDirection),
                    end = paddingValues.calculateEndPadding(localDirection),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.bg_history),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Column(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = paddingValues.calculateTopPadding() + 16.dp
                    )
                    .background(
                        color = Whiteish,
                        shape = RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 10.dp
                        )
                    )
                    .fillMaxSize()
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            top = 32.dp,
                            start = 16.dp,
                            end = 16.dp,
                        ),
                    text = "Logs",
                    fontSize = 20.sp,
                    color = TextDarkBlue,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(12.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp
                    )
                ) {
                    items(historyDateFilters) {
                        FilterChip(
                            selected = it == selectedHistoryDateFilter,
                            onClick = { onSelectHistoryDateFilter(it) },
                            label = {
                                Text(
                                    text = stringResource(it.nameStrResource),
                                    color = if (it == selectedHistoryDateFilter) TextDarkBlue else TextGray,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = Color.Transparent
                            ),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = BgGray
                            )
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                when (attendanceRecordResourceState) {
                    is ResourceState.Error -> {
                        val composition by rememberLottieComposition(
                            spec = LottieCompositionSpec.Asset("error.json")
                        )

                        val progress by animateLottieCompositionAsState(
                            composition = composition,
                            iterations = LottieConstants.IterateForever,
                            speed = 0.5f
                        )

                        Column(
                            modifier = Modifier.fillMaxSize(),
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
                            OutlinedButton(
                                onClick = { onRefresh() },
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    brush = SolidColor(
                                        value = ButtonBgBlue
                                    )
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.refresh),
                                    color = TextDarkBlue
                                )
                            }
                        }
                    }

                    ResourceState.Init -> { /* Do Nothing  */
                    }

                    ResourceState.Loading -> {
                        val composition by rememberLottieComposition(
                            spec = LottieCompositionSpec.Asset("loading.json")
                        )

                        val progress by animateLottieCompositionAsState(
                            composition = composition,
                            iterations = LottieConstants.IterateForever
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            LottieAnimation(
                                composition = composition,
                                progress = { progress }
                            )
                        }
                    }

                    is ResourceState.Success -> {
                        if (attendanceRecordResourceState.data.isEmpty()) {
                            val composition by rememberLottieComposition(
                                spec = LottieCompositionSpec.Asset("empty.json")
                            )

                            val progress by animateLottieCompositionAsState(
                                composition = composition,
                                iterations = LottieConstants.IterateForever,
                                speed = 0.5f
                            )

                            Column(
                                modifier = Modifier.fillMaxSize(),
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
                                    text = stringResource(R.string.empty_statement),
                                    color = TextDarkBlue,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp)
                            ) {
                                items(attendanceRecordResourceState.data) {
                                    AsyncImageListItem(
                                        header = "${it.status} - ${it.officeName}",
                                        subHeader = "${it.date} ${it.hour}",
                                        imageUrl = it.officeImageUrl,
                                        backgroundColor = Color(0xFFFAF9F6),
                                        border = BorderStroke(
                                            width = 1.dp,
                                            color = if (it.status == AttendanceState.CheckIn.value) {
                                                AttBlue
                                            } else {
                                                BgMustard
                                            }
                                        )
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

@Preview
@Composable
fun HistoryScreenSuccessPreview() {
    AttendanceFirebaseTheme {
        HistoryScreen(
            onIconNotificationClick = { },
            selectedHistoryDateFilter = HistoryDateFilter.Day(),
            attendanceRecordResourceState = ResourceState.Success(DataDummy.attendanceRecords),
            onSelectHistoryDateFilter = {},
            onRefresh = {}
        )
    }
}

//@Preview
//@Composable
//fun HistoryScreenSuccessEmptyDataPreview() {
//    AttendanceFirebaseTheme {
//        HistoryScreen(
//            onIconNotificationClick = { },
//            selectedHistoryDateFilter = HistoryDateFilter.Week(),
//            attendanceRecordResourceState = ResourceState.Success(listOf())
//        )
//    }
//}

//@Preview
//@Composable
//fun HistoryScreenLoadingPreview() {
//    AttendanceFirebaseTheme {
//        HistoryScreen(
//            onIconNotificationClick = { },
//            selectedHistoryDateFilter = HistoryDateFilter.Day(),
//            attendanceRecordResourceState = ResourceState.Loading
//        )
//    }
//}

//@Preview
//@Composable
//fun HistoryScreenErrorPreview() {
//    AttendanceFirebaseTheme {
//        HistoryScreen(
//            onIconNotificationClick = { },
//            selectedHistoryDateFilter = HistoryDateFilter.Day(),
//            attendanceRecordResourceState = ResourceState.Error("No Connection"),
//            onSelectHistoryDateFilter = {},
//            onRefreshButtonClick = { }
//        )
//    }
//}