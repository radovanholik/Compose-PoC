package com.example.compose_poc.ui.screen.feature_list

import com.example.compose_poc.ui.common.components.ShareTopBarEvent
import com.example.compose_poc.ui.common.core.BaseViewModel
import com.example.compose_poc.ui.common.core.ViewCommand
import com.example.compose_poc.ui.common.core.ViewEvent
import com.example.compose_poc.ui.common.core.ViewState

class FeatureListViewModel :
    BaseViewModel<FeatureListViewModel.State, FeatureListViewModel.Command>(
        initialState = State.getInitialState()
    ) {

    override fun onEvent(event: ViewEvent) {
        when (event) {
            is ShareTopBarEvent.OnShareClicked -> sendCommand(command = Command.Navigate.Share())
            is FeatureListFragment.Event -> onFragmentEvent(event)
            else -> super.onEvent(event)
        }
    }

    private fun onFragmentEvent(event: FeatureListFragment.Event) {
        when (event) {
            is FeatureListFragment.Event.FeatureClicked -> {
                    when (event.type) {
                        FeatureType.BottomNavigationView -> sendCommand(Command.Navigate.ToBottomNavigationView())
                        FeatureType.Calendar -> sendCommand(Command.Navigate.ToCalendar())
                        FeatureType.Detail -> sendCommand(Command.Navigate.ToDetail())
                        FeatureType.Pager -> sendCommand(Command.Navigate.ToPager())
                        FeatureType.SnackBar -> sendCommand(Command.UI.SnackBar())
                        FeatureType.Toast -> sendCommand(Command.UI.Toast())
                        FeatureType.AlertDialog -> updateState {
                            currentState().copy(isAlertDialogVisible = true)
                        }
                    }
            }
            FeatureListFragment.Event.AlertDialogButtonClicked -> updateState {
                currentState().copy(isAlertDialogVisible = false)
            }
        }
    }

    data class State(
        val features: List<FeatureType>,
        val isAlertDialogVisible: Boolean = false
    ) : ViewState {
        companion object {
            fun getInitialState() = State(
                features = listOf(
                    FeatureType.Toast,
                    FeatureType.SnackBar,
                    FeatureType.Detail,
                    FeatureType.Calendar,
                    FeatureType.Pager,
                    FeatureType.BottomNavigationView,
                    FeatureType.AlertDialog,
                    FeatureType.Toast,
                    FeatureType.SnackBar,
                    FeatureType.Detail,
                    FeatureType.Calendar,
                    FeatureType.Pager,
                    FeatureType.BottomNavigationView,
                    FeatureType.AlertDialog,
                    FeatureType.Toast,
                    FeatureType.SnackBar,
                    FeatureType.Detail,
                    FeatureType.Calendar,
                    FeatureType.Pager,
                    FeatureType.BottomNavigationView,
                    FeatureType.AlertDialog,
                    FeatureType.Toast,
                    FeatureType.SnackBar,
                    FeatureType.Detail,
                    FeatureType.Calendar,
                    FeatureType.Pager,
                    FeatureType.BottomNavigationView,
                    FeatureType.AlertDialog
                )
            )
        }
    }

    sealed class Command : ViewCommand {
        sealed class UI() : Command() {
            class Toast : UI()
            class SnackBar : UI()
        }

        sealed class Navigate() : Command() {
            class Share : Navigate()
            class ToDetail : Navigate()
            class ToCalendar : Navigate()
            class ToPager : Navigate()
            class ToBottomNavigationView : Navigate()
        }
    }
}