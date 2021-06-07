package com.example.compose_poc.ui.screen.viewpager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.compose_poc.R
import com.example.compose_poc.ui.common.core.ComposableFragment
import com.example.compose_poc.ui.theme.AppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.absoluteValue

@InternalCoroutinesApi
@ExperimentalPagerApi
class ViewPagerFragment :
    ComposableFragment<ViewPagerViewModel.State, ViewPagerViewModel.Command>() {
    override val viewModel: ViewPagerViewModel by viewModel()

    @Composable
    override fun ComposableContent(
        scaffoldState: ScaffoldState,
        viewState: ViewPagerViewModel.State
    ) {
        AppTheme(scaffoldState = scaffoldState) {
            val pagerState = rememberPagerState(
                pageCount = 10,
                initialOffscreenLimit = 2
            )

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(id = R.string.pager)
                            )
                        },
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.onBackground
                    )
                },
                scaffoldState = scaffoldState,
                content = {
                    Column(
                        modifier = Modifier
                            .background(color = MaterialTheme.colors.surface)
                            .fillMaxSize()
                    ) {

                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp))

                        HorizontalPagerIndicator(
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                            pagerState = pagerState,
                            activeColor = MaterialTheme.colors.onBackground,
                            inactiveColor = MaterialTheme.colors.background
                        )

                        HorizontalPager(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            itemSpacing = 10.dp,
                            state = pagerState
                        ) { page ->

                            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                            val imageRes = when {
                                page % 4 == 0 -> {
                                    R.drawable.zion_1
                                }
                                page % 4 == 1 -> {
                                    R.drawable.zion_2
                                }
                                page % 4 == 2 -> {
                                    R.drawable.zion_3
                                }
                                page % 4 == 3 -> {
                                    R.drawable.zion_4
                                }
                                else -> R.drawable.zion_1
                            }

                            Card(
                                modifier = Modifier.graphicsLayer {
                                    // We animate the scaleX + scaleY, between 85% and 100%
                                    lerp(
                                        start = 0.85f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    ).also { scale ->
                                        scaleX = scale
                                        scaleY = scale
                                    }

                                    // We animate the alpha, between 50% and 100%
                                    alpha = lerp(
                                        start = 0.5f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )
                                }
                            ) {
                                Column {
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(20.dp)
                                    )

                                    // Our page content
                                    Text(
                                        text = "Page: ${page+1}",
                                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                                    )

                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(20.dp)
                                    )

                                    Image(
                                        painter = painterResource(id = imageRes),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .fillMaxSize()
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}