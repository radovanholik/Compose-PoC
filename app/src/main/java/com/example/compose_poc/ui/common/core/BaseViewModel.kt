package com.example.compose_poc.ui.common.core

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<STATE : ViewState, COMMAND : ViewCommand>(
    val initialState: STATE,
    defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoreViewModel(defaultDispatcher = defaultDispatcher) {

    private val TAG: String = this.javaClass.simpleName

    private val _stateFlow = MutableStateFlow(initialState)

    /**
     * Contains a ViewState instance of [STATE]. Shared through StateFlow
     */
    val stateFlow: Flow<STATE> = _stateFlow.asStateFlow()

    private val _commandFlow = MutableSharedFlow<COMMAND>()

    /**
     * Contains a one-time event [COMMAND] shared through SharedFlow.
     */
    val commandFlow: Flow<COMMAND> = _commandFlow.asSharedFlow()

    /**
     * A channel to handle incoming events from UI.
     */
    val eventChannel = Channel<ViewEvent>(Channel.UNLIMITED)

    init {
        handleEvent()
    }

    /**
     * Updates view state with a new value.
     */
    protected fun updateState(body: STATE.() -> STATE) {
        _stateFlow.value = body(_stateFlow.value)
        Timber.tag(TAG).d("State updated -> ${currentState()}")
    }

    /**
     * Gets the current view state.
     */
    protected fun currentState() = _stateFlow.value

    /**
     * Sends a command to the channel.
     */
    protected fun sendCommand(command: COMMAND) {
        Timber.tag(TAG).d("Sending command: $command")
        viewModelScope.launch { _commandFlow.emit(command) }
    }

    /**
     * Handle a ViewModel specific event.
     */
    protected open fun onEvent(event: ViewEvent) {
        Timber.tag(TAG).e("Unhandled event: $event (${event.javaClass.`package`?.name})")
    }

    private fun handleEvent() {
        viewModelScope.launch {
            eventChannel.consumeAsFlow().collect {
                processEvent(it)
            }
        }
    }

    private fun processEvent(event: ViewEvent) {
        Timber.tag(TAG).i("Incoming event: $event")
        onEvent(event)
    }
}