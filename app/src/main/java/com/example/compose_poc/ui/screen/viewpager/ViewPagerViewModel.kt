package com.example.compose_poc.ui.screen.viewpager

import com.example.compose_poc.ui.common.core.BaseViewModel
import com.example.compose_poc.ui.common.core.ViewCommand
import com.example.compose_poc.ui.common.core.ViewState
import com.example.compose_poc.ui.screen.feature_list.FeatureListViewModel

class ViewPagerViewModel : BaseViewModel<ViewPagerViewModel.State, ViewPagerViewModel.Command>(
    initialState = State()
) {

    class State: ViewState

    sealed class Command : ViewCommand
}