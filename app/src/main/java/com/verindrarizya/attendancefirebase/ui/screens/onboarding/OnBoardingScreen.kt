@file:OptIn(ExperimentalFoundationApi::class)

package com.verindrarizya.attendancefirebase.ui.screens.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.ui.screens.Destination
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.ButtonBgBlue
import com.verindrarizya.attendancefirebase.ui.theme.ButtonTextGray
import com.verindrarizya.attendancefirebase.ui.theme.PagerIndicatorActive
import com.verindrarizya.attendancefirebase.ui.theme.PagerIndicatorInactive

object OnBoardingDestination : Destination {
    override val routeName: String = "OnBoardingDestination"
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onButtonLoginClicked: () -> Unit,
    onButtonSignUpClicked: () -> Unit
) {
    val pageCount = remember { onBoardingPagerItemContentContents.size }
    val pagerState = rememberPagerState()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 16.dp),
            text = "${pagerState.currentPage + 1}/$pageCount",
            textAlign = TextAlign.End,
        )
        Spacer(Modifier.height(16.dp))
        HorizontalPager(
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(
                horizontal = 24.dp,
            ),
            pageSpacing = 24.dp,
            pageCount = pageCount,
            state = pagerState
        ) { pageNumber: Int ->
            OnBoardingPagerItem(
                modifier = Modifier.fillMaxHeight(),
                onBoardingPagerItemContent = onBoardingPagerItemContentContents[pageNumber]
            )
        }
        PagerIndicator(
            pageCount = pageCount,
            pagerState = pagerState,
        )
        Spacer(Modifier.height(50.dp))
        BottomAuthButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            onButtonSignUpClicked = onButtonSignUpClicked,
            onButtonLoginClicked = onButtonLoginClicked
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun OnBoardingPagerItem(
    modifier: Modifier = Modifier,
    onBoardingPagerItemContent: OnBoardingPagerItemContent
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .height(250.dp),
            painter = painterResource(onBoardingPagerItemContent.drawableRes),
            contentDescription = null,
            contentScale = ContentScale.FillHeight
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(onBoardingPagerItemContent.descriptionRes),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    pagerState: PagerState
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration: Int ->
            PagerIndicatorItem(isSelected = pagerState.currentPage == iteration)
        }
    }
}

@Composable
fun PagerIndicatorItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    color: Color = if (isSelected) PagerIndicatorActive else PagerIndicatorInactive,
    size: Dp = if (isSelected) 10.dp else 6.dp
) {
    Box(
        modifier = modifier
            .padding(3.dp)
            .clip(CircleShape)
            .background(color)
            .size(size)
    )
}

@Composable
fun BottomAuthButton(
    modifier: Modifier = Modifier,
    onButtonLoginClicked: () -> Unit,
    onButtonSignUpClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Button(
            modifier = Modifier
                .weight(1f),
            onClick = onButtonLoginClicked,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonBgBlue,
            )
        ) {
            Text(
                text = stringResource(id = R.string.login),
            )
        }
        Spacer(Modifier.width(16.dp))
        OutlinedButton(
            modifier = Modifier
                .weight(1f),
            onClick = onButtonSignUpClicked,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = ButtonTextGray,
            ),
            border = BorderStroke(
                width = 1.dp,
                color = ButtonTextGray
            )
        ) {
            Text(
                text = stringResource(id = R.string.sign_up)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    AttendanceFirebaseTheme {
        OnBoardingScreen(
            onButtonLoginClicked = {},
            onButtonSignUpClicked = {}
        )
    }
}