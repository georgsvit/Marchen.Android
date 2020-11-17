package com.example.marchenandroid.ui.details

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.marchenandroid.R
import com.example.marchenandroid.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        viewModel = DetailsViewModel(requireActivity().application)

        binding.fairytaleViewModel = viewModel
        binding.lifecycleOwner = this
        binding.savesGrid.adapter = DetailsGridAdapter(DetailsGridAdapter.OnClickListener {
            if (it.UnitId != 0) {
                val dialogBuilder = AlertDialog.Builder(requireActivity())

                dialogBuilder.setMessage("Continue or Restart").setTitle("What to do?")

                dialogBuilder.setPositiveButton("Continue") { _, _ ->
                    viewModel.continueGame(it)
                }

                dialogBuilder.setNegativeButton("Restart") { _, _ ->
                    viewModel.restartGame(it)
                }

                dialogBuilder.show()
            } else {
                viewModel.restartGame(it)
            }
        })

        viewModel.selectedChildSave.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                //findNavController().navigate(FragmentDirections.actionFairytaleDetailsFragmentToFairytalePlayFragment(viewModel.selectedChildSave.value!!.UnitId, viewModel.selectedChildSave.value!!.ChildId))
                //viewModel.navigationToSelectedChildSaveComplete()
            }
        })

        return binding.root
    }
}