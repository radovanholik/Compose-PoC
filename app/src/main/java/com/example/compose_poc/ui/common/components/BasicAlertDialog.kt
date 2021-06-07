package com.example.compose_poc.ui.common.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun BasicConfirmAlertDialog(
    isVisible: Boolean,
    title: String,
    confirmButtonText: String,
    message: String,
    onDismissRequest: () -> Unit,
    onConfirmButtonClicked: () -> Unit
) {
    if (isVisible) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(text = title)
            },
            text = {
                Text(
                    text = message
                )
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClicked
                ) {
                    Text(text = confirmButtonText)
                }
            }
        )
    }
}