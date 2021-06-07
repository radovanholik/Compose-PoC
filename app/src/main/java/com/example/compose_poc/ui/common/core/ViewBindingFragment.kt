package com.example.compose_poc.ui.common.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collect
import timber.log.Timber

/**
 * An abstract class that allows to implement a classic android UI with databinding.
 */
abstract class ViewBindingFragment<VB: ViewBinding, S: ViewState, COMMAND: ViewCommand>
    : BaseFragment<S, COMMAND>(), Renderer<S> {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding get() = _binding as VB

    init {
        // Starts listening StateView changes.
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect {
                renderUI(it)
            }
        }

        // Starts listening one-time event changes (Command)
        lifecycleScope.launchWhenStarted {
            viewModel.commandFlow.collect {
                onCommand(it)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = bindingInflater.invoke(inflater, container, false)
        return _binding?.root as View
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun onCommand(command: COMMAND) {
        Timber.tag(javaClass.simpleName).e("Unhandled command: $command (${command.javaClass.`package`?.name}")
    }
}

interface Renderer<S: ViewState> {
    fun renderUI(state: S)
}