package com.verindrarizya.attendancefirebase.ui.screens.dashboard.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ChipDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.core.data.state.AttendanceState
import com.verindrarizya.attendancefirebase.core.entity.AttendanceRecord
import com.verindrarizya.attendancefirebase.ui.composables.widget.AsyncImageListItem
import com.verindrarizya.attendancefirebase.ui.theme.AttBlue
import com.verindrarizya.attendancefirebase.ui.theme.BgGray
import com.verindrarizya.attendancefirebase.ui.theme.BgMustard
import com.verindrarizya.attendancefirebase.ui.theme.ButtonBgBlue
import com.verindrarizya.attendancefirebase.ui.theme.TextDarkBlue
import com.verindrarizya.attendancefirebase.ui.theme.TextGray
import com.verindrarizya.attendancefirebase.ui.theme.Whiteish
import kotlinx.coroutines.launch

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
    val attendanceRecordsPaging = viewModel.attendanceRecordPaging.collectAsLazyPagingItems()

    HistoryScreen(
        modifier = modifier,
        selectedHistoryDateFilter = selectedHistoryDateFilter,
        onSelectHistoryDateFilter = viewModel::selectHistoryDateFilter,
        onRefresh = { attendanceRecordsPaging.refresh() },
        onIconNotificationClick = {},
        attendanceRecordsPaging = attendanceRecordsPaging
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryScreen(
    modifier: Modifier,
    historyDateFilters: List<HistoryDateFilter> = listOfFilter,
    selectedHistoryDateFilter: HistoryDateFilter?,
    onSelectHistoryDateFilter: (HistoryDateFilter) -> Unit,
    onIconNotificationClick: () -> Unit,
    onRefresh: () -> Unit,
    attendanceRecordsPaging: LazyPagingItems<AttendanceRecord>
) {
    val refreshState = rememberPullRefreshState(refreshing = false, onRefresh = onRefresh)
    val filterRowState = rememberLazyListState()
    val screenScope = rememberCoroutineScope()

    val refresh = attendanceRecordsPaging.loadState.refresh

    LaunchedEffect(selectedHistoryDateFilter) {
        screenScope.launch {
            selectedHistoryDateFilter?.let {
                val selectedIndex = historyDateFilters.indexOf(it)
                filterRowState.scrollToItem(selectedIndex)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        backgroundColor = Color.Gray,
    ) { paddingValues: PaddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.bg_history),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Column {
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
                    backgroundColor = Color.Transparent,
                    actions = {
                        IconButton(onClick = onIconNotificationClick) {
                            Icon(
                                Icons.Filled.Notifications,
                                contentDescription = "Notification",
                                tint = Whiteish
                            )
                        }
                    },
                    elevation = 0.dp
                )
                Spacer(Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
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
                        state = filterRowState,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(
                            horizontal = 16.dp
                        )
                    ) {
                        items(historyDateFilters) {
                            FilterChip(
                                shape = RoundedCornerShape(8.dp),
                                selected = it == selectedHistoryDateFilter,
                                onClick = { onSelectHistoryDateFilter(it) },
                                content = {
                                    Text(
                                        modifier = Modifier.padding(
                                            horizontal = 14.dp
                                        ),
                                        text = stringResource(it.nameStrResource),
                                        color = if (it == selectedHistoryDateFilter) TextDarkBlue else TextGray,
                                        fontSize = 14.sp
                                    )
                                },
                                colors = ChipDefaults.filterChipColors(
                                    selectedBackgroundColor = BgGray,
                                    backgroundColor = Color.Transparent
                                )
                            )
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Box(
                        modifier = Modifier.pullRefresh(refreshState)
                    ) {
                        PullRefreshIndicator(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .zIndex(6f),
                            refreshing = false,
                            state = refreshState
                        )

                        when (refresh) {
                            is LoadState.Error -> {
                                val composition by rememberLottieComposition(
                                    spec = LottieCompositionSpec.Asset("error.json")
                                )

                                val progress by animateLottieCompositionAsState(
                                    composition = composition,
                                    iterations = LottieConstants.IterateForever,
                                    speed = 0.5f
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .verticalScroll(rememberScrollState()),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Spacer(Modifier.weight(1f))
                                    LottieAnimation(
                                        modifier = Modifier.size(200.dp),
                                        composition = composition,
                                        progress = { progress },
                                        contentScale = ContentScale.FillWidth
                                    )
                                    Spacer(Modifier.height(12.dp))
                                    OutlinedButton(
                                        onClick = { onRefresh() },
                                        border = ButtonDefaults.outlinedBorder.copy(
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
                                    Spacer(Modifier.weight(1f))
                                }
                            }

                            LoadState.Loading -> {
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

                            is LoadState.NotLoading -> {
                                if (attendanceRecordsPaging.itemCount == 0) {
                                    val composition by rememberLottieComposition(
                                        spec = LottieCompositionSpec.Asset("empty.json")
                                    )

                                    val progress by animateLottieCompositionAsState(
                                        composition = composition,
                                        iterations = LottieConstants.IterateForever,
                                        speed = 0.5f
                                    )

                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .verticalScroll(rememberScrollState()),
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
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                        contentPadding = PaddingValues(
                                            horizontal = 16.dp
                                        )
                                    ) {
                                        attendanceRecordsPaging.apply {
                                            items(itemSnapshotList) { attendanceRecord ->
                                                attendanceRecord?.let {
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
                                            when {
                                                loadState.append is LoadState.Loading -> {
                                                    item {
                                                        Box(
                                                            modifier = Modifier
                                                                .fillMaxWidth(),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            CircularProgressIndicator()
                                                        }
                                                    }
                                                }

                                                loadState.append is LoadState.Error -> {
                                                    item {
                                                        Box(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            OutlinedButton(
                                                                onClick = { onRefresh() },
                                                                border = ButtonDefaults.outlinedBorder.copy(
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
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}