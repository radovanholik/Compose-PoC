package com.example.compose_poc.ui.common.core

import androidx.compose.material.ScaffoldState

interface CommandHandler<COMMAND> {

    /**
     * Reflect one-time command events.
     */
    fun onCommand(command: COMMAND, scaffoldState: ScaffoldState)
}