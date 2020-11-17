package com.example.marchenandroid.ui.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.marchenandroid.R
import com.example.marchenandroid.databinding.FragmentChildBinding

class ChildFragment : Fragment() {
    private lateinit var viewModel: ChildViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentChildBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_child, container, false)

        viewModel = ChildViewModel(requireActivity().application)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.reportsGrid.adapter = ChildGridAdapter(ChildGridAdapter.OnClickListener {
            Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
        })

        binding.editBtn.setOnClickListener {
            //findNavController().navigate(ChildProfileFragmentDirections.actionChildProfileFragmentToChildFormFragment(viewModel.childId.value!!))
        }

        binding.deleteBtn.setOnClickListener {
            //TODO: Add OnDelete Action
        }

        return binding.root
    }

}