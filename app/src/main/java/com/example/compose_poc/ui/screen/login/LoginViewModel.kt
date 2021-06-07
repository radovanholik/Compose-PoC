package com.example.compose_poc.ui.screen.login

import com.example.compose_poc.ui.common.core.BaseViewModel
import com.example.compose_poc.ui.common.core.ViewCommand
import com.example.compose_poc.ui.common.core.ViewEvent
import com.example.compose_poc.ui.common.core.ViewState
import kotlinx.coroutines.delay

class LoginViewModel : BaseViewModel<LoginViewModel.State, LoginViewModel.Command>(
    initialState = State()
) {
    override fun onEvent(event: ViewEvent) {
        when (event) {
            is LoginFragment.Event -> proceedFragmentEvent(event)
            else -> super.onEvent(event)
        }
    }

    private fun proceedFragmentEvent(event: LoginFragment.Event) {
        when (event) {
            is LoginFragment.Event.Username -> {
                updateState { currentState().copy(username = event.value) }
            }
            is LoginFragment.Event.Password -> {
                updateState { currentState().copy(password = event.value) }
            }
            LoginFragment.Event.OnLoginClicked -> login()
            LoginFragment.Event.AlertButtonClicked -> sendCommand(command = Command.Navigation.ToDashboard)
        }
    }

    private fun login() {
        runOnIO {
            updateState { currentState().copy(loading = true)}
            // simulate some kind of validation
            delay(3000)
            if (currentState().username == "username" && currentState().password == "password") {
                updateState { currentState().copy(isLoginSuccessful = true, loading = false) }
            } else {
                updateState { currentState().copy(isLoginSuccessful = false, loading = false) }
            }





        }
    }


    data class State(
        val username: String = "",
        val password: String = "",
        val loading: Boolean = false,
        // just a basic info about the login process
        val isLoginSuccessful: Boolean? = null
    ) : ViewState

    sealed class Command : ViewCommand {
        sealed class Navigation : Command() {
            object ToDashboard : Navigation()
        }
    }
}