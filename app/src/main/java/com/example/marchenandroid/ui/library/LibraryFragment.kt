package com.example.marchenandroid.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.marchenandroid.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {

    private val viewModel: LibraryViewModel by lazy {
        ViewModelProvider(this).get(LibraryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentLibraryBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.libraryViewModel = viewModel
        binding.talesGrid.adapter = LibraryGridAdapter(LibraryGridAdapter.OnClickListener { viewModel.displayFairytaleDetails(it) })

        viewModel.navigateToSelectedFairytale.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                //this.findNavController().navigate(LibraryFragmentDirections.actionLibraryFragment2ToFairytaleDetailsFragment(it))
                //viewModel.displayFairytaleDetailsComplete()
            }
        })

        return binding.root
    }
}