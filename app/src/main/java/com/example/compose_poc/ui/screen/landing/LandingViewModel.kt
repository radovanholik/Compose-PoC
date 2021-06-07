package com.example.compose_poc.ui.screen.landing

import androidx.compose.animation.ExperimentalAnimationApi
import com.example.compose_poc.ui.common.core.BaseViewModel
import com.example.compose_poc.ui.common.core.ViewCommand
import com.example.compose_poc.ui.common.core.ViewEvent
import com.example.compose_poc.ui.common.core.ViewState
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalAnimationApi
class LandingViewModel(

) : BaseViewModel<LandingViewModel.State, LandingViewModel.Command>(
    initialState = State()
) {
    override fun onEvent(event: ViewEvent) {
        when(event) {
            LandingFragment.Event.OnNextButtonClicked -> {
                sendCommand(command = Command.Navigation.ToLogin)
            }
            else -> super.onEvent(event)
        }
    }

    data class State(
        val items: List<LandingContent> = listOf(
                LandingContent(title = "Hello!", message = "Welcome to the latest version of the awesome application!"),
                LandingContent(title = "Really!", message = "The best application on the market!"),
                LandingContent(title = "What's up!", message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."),
                LandingContent(title = "Hello!", message = "So, sign in and enjoy the wonderful ride..")
        )
    ) : ViewState {

        data class LandingContent(
                val title: String,
                val message: String
        )
    }

    sealed class Command : ViewCommand {
        sealed class Navigation: Command() {
            object ToLogin: Navigation()
        }
    }
}