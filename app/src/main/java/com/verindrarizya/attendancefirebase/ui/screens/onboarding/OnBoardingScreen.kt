@file:OptIn(ExperimentalFoundationApi::class)

package com.verindrarizya.attendancefirebase.ui.screens.onboarding

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.verindrarizya.attendancefirebase.R
import com.verindrarizya.attendancefirebase.ui.theme.AttendanceFirebaseTheme
import com.verindrarizya.attendancefirebase.ui.theme.ButtonBgBlue
import com.verindrarizya.attendancefirebase.ui.theme.MontserratFamily
import com.verindrarizya.attendancefirebase.ui.theme.PagerIndicatorActive
import com.verindrarizya.attendancefirebase.ui.theme.PagerIndicatorInactive
import com.verindrarizya.attendancefirebase.ui.theme.TextDarkBlue
import com.verindrarizya.attendancefirebase.ui.theme.TextGray
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onButtonStartedClicked: () -> Unit,
) {
    val pageCount = remember { onBoardingPagerItemContentContents.size }
    val pagerState = rememberPagerState()
    var flagAnimate by rememberSaveable { mutableStateOf(true) }

    val isLastPage by remember {
        derivedStateOf {
            pagerState.currentPage == pageCount - 1
        }
    }

    val buttonAlpha by animateFloatAsState(
        if (isLastPage) 1f else 0f
    )

    val buttonOffset by animateDpAsState(
        if (isLastPage) 0.dp else 16.dp
    )

    LaunchedEffect(Unit) {
        if (flagAnimate) {
            for (i in 0 until pageCount) {
                delay(1_500)
                pagerState.animateScrollToPage(
                    page = i,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            }

            flagAnimate = false
        }
    }

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
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.SemiBold,
            color = TextGray,
            fontSize = 13.sp
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
        Spacer(Modifier.height(40.dp))
        Button(
            modifier = Modifier
                .offset(y = buttonOffset)
                .alpha(buttonAlpha)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            onClick = onButtonStartedClicked,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonBgBlue,
            ),
            enabled = isLastPage,
        ) {
            Text(
                text = stringResource(id = R.string.started),
            )
        }
        Spacer(Modifier.height(24.dp))
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
            lineHeight = 26.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDarkBlue
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
) {

    val size by animateDpAsState(
        targetValue = if (isSelected) 10.dp else 6.dp,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )

    Box(
        modifier = modifier
            .padding(3.dp)
            .clip(CircleShape)
            .background(color)
            .size(size)
    )
}

@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    AttendanceFirebaseTheme {
        OnBoardingScreen(
            onButtonStartedClicked = {},
        )
    }
}