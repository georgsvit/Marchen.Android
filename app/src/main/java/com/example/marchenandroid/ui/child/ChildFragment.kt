package com.example.marchenandroid.ui.child

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.marchenandroid.MainActivity
import com.example.marchenandroid.R
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.databinding.FragmentChildBinding
import com.example.marchenandroid.ui.awards.AwardsActivity
import com.example.marchenandroid.ui.child_form.ChildFormActivity
import com.example.marchenandroid.ui.children.ChildrenViewModel
import com.example.marchenandroid.ui.reports.ReportsActivity
import com.example.marchenandroid.ui.viewer.ViewerActivity
import kotlinx.android.synthetic.main.fragment_child.*

class ChildFragment : Fragment() {
    private lateinit var viewModel: ChildViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentChildBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_child, container, false)

        viewModel = ChildViewModel(requireActivity().application)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.reportsBtn.setOnClickListener {
            viewModel.saveChildIdToSP()
            startActivity(Intent(context, ReportsActivity::class.java))
        }

        binding.awardsBtn.setOnClickListener {
            viewModel.saveChildIdToSP()
            startActivity(Intent(context, AwardsActivity::class.java))
        }

        viewModel.deleteStatus.observe(viewLifecycleOwner, Observer { newStatus ->
            when (newStatus) {
                ApiStatus.ERROR -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
                ApiStatus.DONE -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    activity?.finish()
                }
                else -> Toast.makeText(context, "In a process", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.child.observe(viewLifecycleOwner, Observer {
            nameTextView.text = it.Firstname
            surnameTextView.text = it.Lastname

            //TODO: Set normal avatar default picture
            if (it.AvatarURL == null) {
                it.AvatarURL = "https://1.bp.blogspot.com/_16lyaJiGldI/TBekxqet-JI/AAAAAAAAAis/jTGCN4Wfo8Q/s1600/smile.jpg"
            }

            val imgUri = it.AvatarURL.toUri().buildUpon().scheme("https").build()
            Glide.with(avatar.context)
                    .load(imgUri)
                    .apply(RequestOptions().placeholder(R.drawable.loading_animation).error(R.drawable.ic_broken_image))
                    .into(avatar)
        })

        if (viewModel.userRole.value == 2) {
            setHasOptionsMenu(true)
        }

        return binding.root
    }

    private fun delete() {
        val dialogBuilder = AlertDialog.Builder(requireActivity())

        dialogBuilder.setMessage("Do you want to delete this child?\nRemoved data couldn't be restored").setTitle("Delete?")

        dialogBuilder.setPositiveButton("Delete") { _, _ ->
            viewModel.delete()
            requireActivity().finish()
            startActivity(Intent(context, MainActivity::class.java))
        }

        dialogBuilder.setNegativeButton("Cancel") { _, _ -> }

        dialogBuilder.show()
    }

    private fun edit() {
        viewModel.saveChildIdToSP()
        startActivity(Intent(context, ChildFormActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.child_menu, menu)

        if (viewModel == null) {
            menu.findItem(R.id.deleteBtn)?.isVisible = false
            menu.findItem(R.id.editBtn)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteBtn -> delete()
            R.id.editBtn -> edit()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        viewModel.globalGetChild()
        super.onResume()
    }
}