package com.example.marchenandroid.ui.reports

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.marchenandroid.R
import com.example.marchenandroid.databinding.FragmentReportsBinding
import com.example.marchenandroid.ui.viewer.ViewerActivity

class ReportsFragment : Fragment() {
    private lateinit var viewModel: ReportsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentReportsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reports, container, false)

        viewModel = ReportsViewModel(requireActivity().application)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.reportsGrid.adapter = ReportsGridAdapter(ReportsGridAdapter.OnClickListener {
            startActivity(Intent(context, ViewerActivity::class.java).putExtra("reportId", it.Id))
        })

        return binding.root
    }

}