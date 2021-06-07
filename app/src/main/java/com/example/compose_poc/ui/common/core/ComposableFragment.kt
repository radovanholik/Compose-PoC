package com.example.compose_poc.ui.common.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose_poc.ui.common.components.util.toastShort
import com.example.compose_poc.ui.screen.feature_list.FeatureListViewModel
import timber.log.Timber

abstract class ComposableFragment<S: ViewState, Command: ViewCommand>: BaseFragment<S, Command>(), CommandHandler<Command> {

    override fun onCommand(command: Command, scaffoldState: ScaffoldState) {
        Timber.tag(javaClass.simpleName).e("Unhandled command: $command (${command.javaClass.`package`?.name}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                val viewState by remember(viewModel) {
                    viewModel.stateFlow
                }.collectAsState(initial = viewModel.initialState)

                val command = viewModel.commandFlow.collectAsState(initial = null).value

                val scaffoldState = rememberScaffoldState(snackbarHostState = SnackbarHostState())

                // Run launch effect in order to handle one-time event (command) and propagate it
                // to onCommand() method
                LaunchedEffect(command) {
                    command ?.let { onCommand(command, scaffoldState) }
                }

                ComposableContent(scaffoldState, viewState)
            }
        }
    }

    @Composable
    abstract fun ComposableContent(scaffoldState: ScaffoldState, viewState: S)
}