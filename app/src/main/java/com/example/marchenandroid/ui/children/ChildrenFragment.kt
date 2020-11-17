package com.example.marchenandroid.ui.children

import android.content.Intent
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
import com.example.marchenandroid.ui.child.ChildActivity
import com.example.marchenandroid.ui.child_form.ChildFormActivity

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
                viewModel.saveChildIdToSP(it.Id)
                startActivity(Intent(context, ChildActivity::class.java))
                viewModel.displayChildDetailsComplete()
            }
        })

        binding.createBtn.setOnClickListener {
            viewModel.saveChildIdToSP(0)
            startActivity(Intent(context, ChildFormActivity::class.java))
        }

        return binding.root
    }

    override fun onResume() {
        viewModel = ViewModelProvider(this).get(ChildrenViewModel::class.java)
        viewModel.globalGetChildren()
        super.onResume()
    }
}