package com.example.compose_poc.ui.screen.feature_list

import androidx.annotation.StringRes
import com.example.compose_poc.R

sealed class FeatureType(@StringRes val title: Int) {
    object Toast : FeatureType(title = R.string.toast)
    object SnackBar : FeatureType(title = R.string.snackbar)
    object Detail : FeatureType(title = R.string.detail)
    object Calendar : FeatureType(title = R.string.calendar)
    object Pager : FeatureType(title = R.string.pager)
    object BottomNavigationView : FeatureType(title = R.string.bottom_navigation_view)
    object AlertDialog : FeatureType(title = R.string.alert_dialog)
}