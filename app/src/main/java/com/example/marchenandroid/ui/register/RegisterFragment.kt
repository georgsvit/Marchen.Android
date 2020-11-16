package com.example.marchenandroid.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.marchenandroid.R
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        val binding : FragmentRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        val email = binding.emailInput
        val password = binding.passwordInput
        val fullname = binding.fullnameInput
        val role = binding.roleInput.checkedRadioButtonId

        binding.registerViewModel = viewModel

        email.doAfterTextChanged {
            viewModel.registerDataChanged(email.text.toString(), password.text.toString(), fullname.text.toString(), role)
        }
        password.doAfterTextChanged {
            viewModel.registerDataChanged(email.text.toString(), password.text.toString(), fullname.text.toString(), role)
        }
        fullname.doAfterTextChanged {
            viewModel.registerDataChanged(email.text.toString(), password.text.toString(), fullname.text.toString(), role)
        }

        binding.registerBtn.setOnClickListener {
            val roleVal = when {
                binding.radioButton.isChecked -> {
                    1
                }
                binding.radioButton2.isChecked -> {
                    2
                }
                else -> 0
            }
            viewModel.onRegisterClick(email.text.toString(), password.text.toString(), fullname.text.toString(), roleVal)
        }

        binding.radioButton.setOnClickListener {
            viewModel.registerDataChanged(email.text.toString(), password.text.toString(), fullname.text.toString(), 1)
            binding.radioButton.setError(null)
            binding.radioButton2.setError(null)
        }

        binding.radioButton2.setOnClickListener {
            viewModel.registerDataChanged(email.text.toString(), password.text.toString(), fullname.text.toString(), 2)
            binding.radioButton.setError(null)
            binding.radioButton2.setError(null)
        }

        viewModel.registerFormState.observe(viewLifecycleOwner, Observer { formState ->
            binding.registerBtn.isEnabled = formState.isDataValid
            if (formState.emailError != null) {
                email.error = getString(formState.emailError)
            }
            if (formState.passwordError != null) {
                password.error = getString(formState.passwordError)
            }
            if (formState.fullnameError != null) {
                fullname.error = getString(formState.fullnameError)
            }
            if (formState.roleError != null) {
                binding.radioButton.error = getString(formState.roleError)
                binding.radioButton2.error = getString(formState.roleError)
            }
        })

        viewModel.status.observe(viewLifecycleOwner, Observer { newStatus ->
            when (newStatus) {
                ApiStatus.ERROR -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                ApiStatus.DONE -> {
                    // Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    //findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToAccountFragment())
                }
                ApiStatus.LOADING -> Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(context, "In a process", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }
}