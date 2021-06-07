package com.example.compose_poc.ui.common.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseFragment<STATE: ViewState, COMMAND: ViewCommand> : Fragment() {

    abstract val viewModel: BaseViewModel<STATE, COMMAND>

    /**
     * The method sends an [event] (UI action) to an associated [viewModel] instance through [Channel]
     */
    protected fun sendEvent(event: ViewEvent) {
        lifecycleScope.launch {
            viewModel.eventChannel.send(event)
        }
    }
}