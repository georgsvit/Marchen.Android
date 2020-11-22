package com.example.marchenandroid.ui.child

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.marchenandroid.R
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.databinding.FragmentChildBinding
import com.example.marchenandroid.ui.child_form.ChildFormActivity
import com.example.marchenandroid.ui.viewer.ViewerActivity
import kotlinx.android.synthetic.main.fragment_child.*

class ChildFragment : Fragment() {
    private lateinit var viewModel: ChildViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentChildBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_child, container, false)

        viewModel = ChildViewModel(requireActivity().application)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.reportsGrid.adapter = ChildGridAdapter(ChildGridAdapter.OnClickListener {
            startActivity(Intent(context, ViewerActivity::class.java).putExtra("reportId", it.Id))
        })

        binding.editBtn.setOnClickListener {
            viewModel.saveChildIdToSP()
            startActivity(Intent(context, ChildFormActivity::class.java))
        }

        binding.deleteBtn.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireActivity())

            dialogBuilder.setMessage("Do you want to delete this child?\nRemoved data couldn't be restored").setTitle("Delete?")

            dialogBuilder.setPositiveButton("Delete") { _, _ ->
                viewModel.delete()
            }

            dialogBuilder.setNegativeButton("Cancel") { _, _ -> }

            dialogBuilder.show()
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
                it.AvatarURL = "https://upload.wikimedia.org/wikipedia/commons/e/ec/Dana_White_-_London_2015_%28cropped%29.jpg"
            }

            val imgUri = it.AvatarURL.toUri().buildUpon().scheme("https").build()
            Glide.with(avatar.context)
                    .load(imgUri)
                    .apply(RequestOptions().placeholder(R.drawable.loading_animation).error(R.drawable.ic_broken_image))
                    .into(avatar)
        })

        return binding.root
    }

}