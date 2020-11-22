package com.example.marchenandroid.ui.awards

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.marchenandroid.R
import com.example.marchenandroid.databinding.FragmentAwardsBinding
import com.example.marchenandroid.databinding.FragmentReportsBinding
import com.example.marchenandroid.ui.reports.ReportsGridAdapter
import com.example.marchenandroid.ui.reports.ReportsViewModel
import com.example.marchenandroid.ui.viewer.ViewerActivity

class AwardsFragment : Fragment() {
    private lateinit var viewModel: AwardsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentAwardsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_awards, container, false)

        viewModel = AwardsViewModel(requireActivity().application)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.awardsGrid.adapter = AwardsGridAdapter(AwardsGridAdapter.OnClickListener {
            Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }

}