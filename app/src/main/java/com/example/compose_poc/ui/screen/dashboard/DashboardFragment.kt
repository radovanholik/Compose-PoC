package com.example.compose_poc.ui.screen.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.compose_poc.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNavView: BottomNavigationView = view.findViewById(R.id.nav_view)

        val nestedNavHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as? NavHostFragment
        val navController = nestedNavHostFragment?.navController

        if (navController != null) {
            bottomNavView.setupWithNavController(navController)
        } else {
            throw RuntimeException("Controller not found")
        }
    }
}