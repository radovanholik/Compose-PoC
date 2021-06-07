package com.example.compose_poc.ui.screen.landing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.example.compose_poc.R
import com.example.compose_poc.ui.common.core.ComposableFragment
import com.example.compose_poc.ui.common.core.ViewEvent
import com.example.compose_poc.ui.theme.AppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalAnimationApi
@ExperimentalPagerApi
class LandingFragment : ComposableFragment<LandingViewModel.State, LandingViewModel.Command>() {

    override val viewModel: LandingViewModel by viewModel()

    override fun onCommand(command: LandingViewModel.Command, scaffoldState: ScaffoldState) {
        when (command) {
            LandingViewModel.Command.Navigation.ToLogin -> {
                // Navigate to login screen
                findNavController().navigate(LandingFragmentDirections.toLogin())
            }
        }
    }

    @Composable
    override fun ComposableContent(
        scaffoldState: ScaffoldState,
        viewState: LandingViewModel.State
    ) {
        val pagerState = rememberPagerState(
                pageCount = viewState.items.size,
                initialOffscreenLimit = 2
        )
        val isLastPageShown = pagerState.currentPage == pagerState.pageCount - 1

        AppTheme(scaffoldState = scaffoldState) {
            Column {
                Box(
                        modifier = Modifier.height(250.dp)
                ) {
                    // top section image
                    Image(
                            modifier = Modifier
                                    .height(300.dp)
                                    .fillMaxWidth(),
                            painter = painterResource(id = R.drawable.zion_canyon),
                            contentDescription = "Zion - Angel's Landing",
                            contentScale = ContentScale.Crop
                    )

                    HorizontalPagerIndicator(
                            pagerState = pagerState,
                            modifier = Modifier
                                .align(alignment = Alignment.BottomCenter)
                                .padding(15.dp),
                            activeColor = Color.White,
                            inactiveColor = Color.Gray
                    )
                }

                Box {
                    // View Pager
                    HorizontalPager(
                            modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 60.dp),
                            state = pagerState
                    ) { page ->
                        val item = viewState.items[page]

                        Column(
                                modifier = Modifier.padding(15.dp)
                        ) {
                            // title
                            Text(
                                    text = item.title,
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    style = MaterialTheme.typography.h2
                            )
                            // Message
                            Text(
                                    text = item.message,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h5
                            )
                        }
                    }

                    Column(
                            modifier = Modifier
                                    .align(alignment = Alignment.BottomCenter)
                                    .padding(15.dp)
                    ) {
                        AnimatedVisibility(
                                visible = isLastPageShown
                        ) {
                            Button(
                                    onClick = { sendEvent(Event.OnNextButtonClicked) },
                            ) {
                                Text("Next!")
                            }
                        }
                    }
                }
            }
        }
    }

    sealed class Event: ViewEvent {
        object OnNextButtonClicked : Event()
    }
}