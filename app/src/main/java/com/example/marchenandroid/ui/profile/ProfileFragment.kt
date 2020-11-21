package com.example.marchenandroid.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.marchenandroid.MainActivity
import com.example.marchenandroid.R
import com.example.marchenandroid.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.accountViewModel = viewModel

        binding.quit.setOnClickListener {
            viewModel.quit()
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
            //findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToLoginFragment())
        }

        binding.getIdBtn.setOnClickListener {
            val id = viewModel.getId()

            Toast.makeText(context, "Your Id: $id", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }
}