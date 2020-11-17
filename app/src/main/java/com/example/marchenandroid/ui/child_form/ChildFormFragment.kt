package com.example.marchenandroid.ui.child_form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.marchenandroid.R
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.databinding.FragmentChildFormBinding

class ChildFormFragment : Fragment() {
    private lateinit var viewModel: ChildFormViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentChildFormBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_child_form, container, false)
        val name = binding.nameField
        val surname = binding.surnameField

        viewModel = ChildFormViewModel(requireActivity().application)
        binding.childFormViewModel = viewModel

        name.doAfterTextChanged {
            viewModel.registerDataChanged(name.text.toString(), surname.text.toString())
        }

        surname.doAfterTextChanged {
            viewModel.registerDataChanged(name.text.toString(), surname.text.toString())
        }

        binding.saveBtn.setOnClickListener {
            viewModel.onSaveClick(name.text.toString(), surname.text.toString())
        }

        binding.cancelBtn.setOnClickListener {
            activity?.finish()
        }

        viewModel.status.observe(viewLifecycleOwner, Observer { newStatus ->
            when (newStatus) {
                ApiStatus.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
                ApiStatus.DONE -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    activity?.finish()
                }
                ApiStatus.LOADING -> binding.progressBar.visibility = View.VISIBLE
                else -> Toast.makeText(context, "In a process", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.registerFormState.observe(viewLifecycleOwner, Observer { formState ->
            binding.saveBtn.isEnabled = formState.isDataValid
            if (formState.nameError != null) {
                name.error = getString(formState.nameError)
            }
            if (formState.surnameError != null) {
                surname.error = getString(formState.surnameError)
            }
        })

        return binding.root
    }

}