package com.example.compose_poc.ui.common.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * According to google - dispatchers should be always provided.
 */
abstract class CoreViewModel(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    protected fun runOnIO(block: suspend CoroutineScope.() -> Unit) =
        run(Dispatchers.IO) { block() }

    protected fun runOnMain(block: suspend CoroutineScope.() -> Unit) =
        run(Dispatchers.Main) { block() }

    private fun run(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(context) { block() }
}