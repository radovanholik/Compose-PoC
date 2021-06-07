package com.example.compose_poc.ui.screen.dashboard2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.compose_poc.R

class DashboardFragment2 : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel2


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel2::class.java)
        val view = inflater.inflate(R.layout.fragment_dashboard2, container, false)

        val textView: TextView = view.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return view
    }
}