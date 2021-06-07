package com.example.compose_poc.ui.screen.feature_list

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.compose_poc.R
import com.example.compose_poc.ui.common.components.BasicConfirmAlertDialog
import com.example.compose_poc.ui.common.components.FeatureCard
import com.example.compose_poc.ui.common.components.ShareTopBar
import com.example.compose_poc.ui.common.components.util.toastShort
import com.example.compose_poc.ui.common.core.ComposableFragment
import com.example.compose_poc.ui.common.core.ViewEvent
import com.example.compose_poc.ui.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeatureListFragment :
    ComposableFragment<FeatureListViewModel.State, FeatureListViewModel.Command>() {

    override val viewModel: FeatureListViewModel by viewModel()

    override fun onCommand(
        command: FeatureListViewModel.Command,
        scaffoldState: ScaffoldState
    ) {
        when (command) {
            is FeatureListViewModel.Command.Navigate -> onCommandNavigate(command)
            is FeatureListViewModel.Command.UI -> {
                when (command) {
                    is FeatureListViewModel.Command.UI.SnackBar -> {
                        lifecycleScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Some super cool custom snackbar.")
                        }
                    }
                    is FeatureListViewModel.Command.UI.Toast -> requireContext().toastShort("Toto je toast")
                }
            }
            else -> super.onCommand(command, scaffoldState)
        }
    }

    private fun onCommandNavigate(command: FeatureListViewModel.Command.Navigate) {
        when (command) {
            is FeatureListViewModel.Command.Navigate.ToBottomNavigationView -> {
                findNavController().navigate(FeatureListFragmentDirections.toDashboard())
            }
            is FeatureListViewModel.Command.Navigate.ToCalendar -> {
                findNavController().navigate(FeatureListFragmentDirections.toCalendar())
            }
            is FeatureListViewModel.Command.Navigate.ToDetail -> {
                requireContext().toastShort("Not implemented yet.")
            }
            is FeatureListViewModel.Command.Navigate.ToPager -> {
                findNavController().navigate(FeatureListFragmentDirections.toViewPager())
            }
            is FeatureListViewModel.Command.Navigate.Share -> {
                // just try to share some dummy data
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "This is my text to share.")
                    type = "text/plain"
                }

                Intent.createChooser(sendIntent, null).let { shareIntent ->
                    requireContext().startActivity(shareIntent)
                }
            }
        }
    }

    @Composable
    override fun ComposableContent(
        scaffoldState: ScaffoldState,
        viewState: FeatureListViewModel.State
    ) {
        AppTheme(
            scaffoldState = scaffoldState
        ) {
            Scaffold(
                topBar = {
                    ShareTopBar(titleRes = R.string.features, onEvent = this::sendEvent)
                },
                scaffoldState = scaffoldState,
                content = {
                    Box(
                        modifier = Modifier
                            .background(color = MaterialTheme.colors.surface)
                            .fillMaxSize()
                    ) {
                        ListContent(features = viewState.features)
                    }

                    BasicConfirmAlertDialog(
                        isVisible = viewState.isAlertDialogVisible,
                        title = "Alert dialog example",
                        message = "This is just AN alert dialog example showing some textual content.",
                        confirmButtonText = "CLOSE",
                        onDismissRequest = { sendEvent(Event.AlertDialogButtonClicked) },
                        onConfirmButtonClicked = { sendEvent(Event.AlertDialogButtonClicked) }
                    )
                }
            )
        }
    }

    @Composable
    private fun ListContent(features: List<FeatureType>) {
        LazyColumn {
            items(
                items = features
            ) { featureType ->
                FeatureCard(
                    type = featureType,
                    onClick = {
                        sendEvent(Event.FeatureClicked(type = featureType))
                    })
            }
        }
    }

    sealed class Event : ViewEvent {
        class FeatureClicked(val type: FeatureType) : Event()
        object AlertDialogButtonClicked: Event()
    }
}