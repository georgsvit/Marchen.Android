package com.example.marchenandroid.ui.login

import android.content.Intent
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
import androidx.navigation.fragment.findNavController
import com.example.marchenandroid.HomeActivity
import com.example.marchenandroid.R
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.databinding.FragmentLoginBinding
import java.util.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        val email = binding.loginInput
        val password = binding.passwordInput

        binding.loginViewModel = viewModel

        email.doAfterTextChanged {
            viewModel.loginDataChanged(email = email.text.toString(), password = password.text.toString())
        }

        password.doAfterTextChanged {
            viewModel.loginDataChanged(email = email.text.toString(), password = password.text.toString())
        }

        binding.loginBtn.setOnClickListener {
            viewModel.onLoginClick(email = email.text.toString(), password = password.text.toString())
        }

        binding.registerBtn.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        viewModel.tokenDate.observe(viewLifecycleOwner, Observer {  date ->
            if (date != null && date > Calendar.getInstance().time) {
                startActivity(Intent(context, HomeActivity::class.java))
                activity?.finish()
                //findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToAccountFragment())
            }
        })

        viewModel.status.observe(viewLifecycleOwner, Observer { newStatus ->
            when (newStatus) {
                ApiStatus.ERROR -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                ApiStatus.DONE -> {
                    startActivity(Intent(context, HomeActivity::class.java))
                    // Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    //findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToAccountFragment())
                }
                ApiStatus.LOADING -> Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(context, "In a process", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            viewModel.loginFormState.observe(viewLifecycleOwner, Observer { formState ->
                binding.loginBtn.isEnabled = formState.isDataValid
                if (formState.emailError != null) {
                    email.error = getString(formState.emailError)
                }
                if (formState.passwordError != null) {
                    password.error = getString(formState.passwordError)
                }
            })
        })

        return binding.root
    }
}