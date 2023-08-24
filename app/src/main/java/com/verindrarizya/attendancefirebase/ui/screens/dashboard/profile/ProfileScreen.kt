package com.verindrarizya.attendancefirebase.ui.screens.dashboard.profile

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.ui.composables.widget.IconListItem
import com.verindrarizya.attendancefirebase.ui.composables.widget.ListItem
import com.verindrarizya.attendancefirebase.ui.composables.widget.LoadingDialog
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
    val context = LocalContext.current

    val profileUiState by viewModel.profileUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.message.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    ProfileScreen(
        modifier = modifier,
        profileUiState = profileUiState,
        onRefresh = viewModel::refresh,
        onSignOutClick = viewModel::signOut,
        onChangePasswordClick = {},
        onIconEditClick = {
            Toast.makeText(
                context,
                "Please contact office's admin for changing data",
                Toast.LENGTH_SHORT
            ).show()
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileUiState: ProfileUiState,
    onRefresh: () -> Unit,
    onSignOutClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onIconEditClick: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    val refreshState = rememberPullRefreshState(
        refreshing = profileUiState.isRefreshing,
        onRefresh = onRefresh
    )

    if (profileUiState.isLoading) {
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
                refreshing = profileUiState.isRefreshing,
                state = refreshState,
            )

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
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
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Profile",
                                        color = Whiteish,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                },
                                backgroundColor = Color.Transparent,
                                actions = {
                                    IconButton(onClick = onIconEditClick) {
                                        Icon(
                                            painterResource(id = R.drawable.ic_edit),
                                            contentDescription = "Edit Profile",
                                            tint = Whiteish
                                        )
                                    }
                                },
                                elevation = 0.dp
                            )
                            Spacer(
                                Modifier.height(20.dp)
                            )
                            ProfileCard(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 4.dp
                                    ),
                                name = profileUiState.username,
                                jobTitle = profileUiState.jobTitle,
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
                    IconListItem(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 12.dp
                            ),
                        header = "No. Karyawan",
                        subHeader = profileUiState.employeeNumber,
                        painter = painterResource(R.drawable.ic_employee_id)
                    )
                }
                item {
                    IconListItem(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 12.dp
                            ),
                        header = "Alamat",
                        subHeader = profileUiState.address,
                        painter = painterResource(R.drawable.ic_address)
                    )
                }
                item {
                    IconListItem(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 12.dp
                            ),
                        header = "Change Password",
                        painter = painterResource(R.drawable.ic_lock),
                        onClick = onChangePasswordClick
                    )
                }
                item {
                    IconListItem(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 12.dp
                            ),
                        header = "Sign Out",
                        painter = painterResource(R.drawable.ic_logout),
                        onClick = onSignOutClick
                    )
                }
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
        backgroundColor = Whiteish,
        shape = RoundedCornerShape(12.dp)
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
            profileUiState = ProfileUiState(
                username = "Verindra Rizya",
                jobTitle = "Android Developer",
                address = "Jalan Semangka",
                employeeNumber = "19220799"
            ),
            onRefresh = {},
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