package com.example.marchenandroid.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.URLUtil
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
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
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_profile,
                container,
                false
        )
        binding.accountViewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun getTeacherId() {
        val id = viewModel.getTeacherId()
        Toast.makeText(context, "Your Id: $id", Toast.LENGTH_LONG).show()
    }

    private fun quit() {
        viewModel.quit()
        requireActivity().finish()
        startActivity(Intent(context, MainActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_menu, menu)

        if (viewModel == null) {
            menu.findItem(R.id.logout)?.isVisible = false
        }
        if (viewModel.userRole.value == 2) {
            menu.findItem(R.id.getId)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> quit()
            R.id.getId -> getTeacherId()
        }
        return super.onOptionsItemSelected(item)
    }
}