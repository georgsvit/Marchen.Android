package com.example.marchenandroid.ui.children

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.marchenandroid.R
import com.example.marchenandroid.databinding.FragmentChildrenBinding

class ChildrenFragment : Fragment() {
    private lateinit var viewModel: ChildrenViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentChildrenBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_children, container, false)
        viewModel = ViewModelProvider(this).get(ChildrenViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.childrenGrid.adapter = ChildrenGridAdapter(ChildrenGridAdapter.OnClickListener { viewModel.displayChildDetails(it) })

        viewModel.navigateToSelectedChild.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                //this.findNavController().navigate(ChildrenFragmentDirections.actionChildrenFragmentToChildProfileFragment(it.Id))
                viewModel.displayChildDetailsComplete()
            }
        })

        binding.createBtn.setOnClickListener {
            //this.findNavController().navigate(ChildrenFragmentDirections.actionChildrenFragmentToChildFormFragment(0))
        }

        return binding.root
    }
}