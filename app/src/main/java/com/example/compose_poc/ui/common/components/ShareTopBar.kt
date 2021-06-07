package com.example.compose_poc.ui.common.components

import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.compose_poc.ui.common.core.ViewEvent

/**
 * A basic [TopAppBar] with additional share icon as action.
 * @param titleRes A string resource to show. Default value -1 in order to show nothing.
 * @param onEvent Represents a [ShareTopBarEvent] action associated with the [ShareTopBar]
 *
 * e.g.
 * [ShareTopBarEvent.OnShareClicked]
 *
 * @see [ViewEvent]
 */
@Composable
fun ShareTopBar(@StringRes titleRes: Int, onEvent: (ViewEvent) -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = titleRes)
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        actions = {
            IconButton(
                onClick = {
                    onEvent(ShareTopBarEvent.OnShareClicked)
                }
            ) {
                Icon(Icons.Filled.Share, contentDescription = "Share")
            }
        }
    )
}

sealed class ShareTopBarEvent : ViewEvent {
    object OnShareClicked : ShareTopBarEvent()
}

