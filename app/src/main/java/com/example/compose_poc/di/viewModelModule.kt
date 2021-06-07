package com.example.compose_poc.di

import androidx.compose.animation.ExperimentalAnimationApi
import com.example.compose_poc.ui.screen.feature_list.FeatureListViewModel
import com.example.compose_poc.ui.screen.landing.LandingViewModel
import com.example.compose_poc.ui.screen.login.LoginViewModel
import com.example.compose_poc.ui.screen.viewpager.ViewPagerViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalPagerApi
@ExperimentalAnimationApi
val viewModelModule = module {
    viewModel { LandingViewModel() }
    viewModel { LoginViewModel() }
    viewModel { FeatureListViewModel() }
    viewModel { ViewPagerViewModel() }
}