package com.example.compose_poc.ui.screen.calendar

import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import com.example.compose_poc.R
import com.example.compose_poc.ui.common.components.util.toastShort
import com.example.compose_poc.ui.theme.AppTheme
import com.google.accompanist.pager.rememberPagerState
import java.util.*

class CalendarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val scaffoldState = rememberScaffoldState()

                AppTheme(scaffoldState = scaffoldState) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.pager)
                                    )
                                },
                                backgroundColor = MaterialTheme.colors.background,
                                contentColor = MaterialTheme.colors.onBackground
                            )
                        },
                        scaffoldState = scaffoldState,
                        content = {
                            Column(
                                modifier = Modifier
                                    .background(color = MaterialTheme.colors.surface)
                                    .fillMaxSize()
                            ) {
                                AndroidView(
                                    modifier = Modifier.fillMaxWidth(),
                                    factory = {
                                        CalendarView(context).apply {
                                            this.date = Date().time
                                            this.setOnDateChangeListener { view, year, month, dayOfMonth ->
                                                requireContext().toastShort("$year/$month/$dayOfMonth ")
                                            }
                                        }
                                    },
                                    update = { view ->
                                        // apply state changes to the view here
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}