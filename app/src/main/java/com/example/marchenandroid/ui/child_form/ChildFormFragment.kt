package com.example.marchenandroid.ui.child_form

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
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
import java.time.LocalDate
import java.time.Month
import java.util.*

class ChildFormFragment : Fragment() {
    private lateinit var viewModel: ChildFormViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentChildFormBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_child_form, container, false)
        val name = binding.nameField
        val surname = binding.surnameField
        val teacher = binding.teacherIdField

        viewModel = ChildFormViewModel(requireActivity().application)
        binding.childFormViewModel = viewModel

        name.doAfterTextChanged {
            if (surname.text.toString() != "") {
                viewModel.dataChanged(name.text.toString(), surname.text.toString(), teacher.text.toString())//Integer.parseInt(teacher.text.toString()))
            }
        }

        surname.doAfterTextChanged {
            if (name.text.toString() != "") {
                viewModel.dataChanged(name.text.toString(), surname.text.toString(), teacher.text.toString())//Integer.parseInt(teacher.text.toString()))
            }
        }

        teacher.doAfterTextChanged {
            viewModel.dataChanged(name.text.toString(), surname.text.toString(), teacher.text.toString())//Integer.parseInt(teacher.text.toString()))
        }

        binding.saveBtn.setOnClickListener {
            val id = if (teacher.text.toString() == "") {
                0
            } else {
                Integer.parseInt(teacher.text.toString())
            }

            if (viewModel.selectedAvatar.value == null) {
                Toast.makeText(context, "Avatar wasn't selected", Toast.LENGTH_SHORT).show()
            } else if (viewModel.dob.value == null) {
                Toast.makeText(context, "Birth date wasn't selected", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.onSaveClick(name.text.toString(), surname.text.toString(), id)
            }
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

        viewModel.child.observe(viewLifecycleOwner, Observer {
            name.setText(it.Firstname)
            surname.setText(it.Lastname)
            teacher.setText(it.TeacherId.toString())
        })

        viewModel.formState.observe(viewLifecycleOwner, Observer { formState ->
            binding.saveBtn.isEnabled = formState.isDataValid
            if (formState.nameError != null) {
                name.error = getString(formState.nameError)
            }
            if (formState.surnameError != null) {
                surname.error = getString(formState.surnameError)
            }
            if (formState.teacherError != null) {
                teacher.error = getString(formState.teacherError)
            }
            if (formState.avatarError != null) {
                Toast.makeText(context, getString(formState.avatarError), Toast.LENGTH_SHORT).show()
            }
        })

        binding.lifecycleOwner = this
        binding.avatarsGrid.adapter = AvatarsGridAdapter(AvatarsGridAdapter.OnClickListener {
            viewModel.setAvatar(it)
            it.isSelected = true
            binding.avatarsGrid.adapter!!.notifyDataSetChanged()
            viewModel.dataChanged(name.text.toString(), surname.text.toString(), teacher.text.toString())
        })

        viewModel.dob.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.dobBtn.text = "${it.dayOfMonth.toString()}/${it.monthValue}/${it.year.toString()}"
            } else {
                binding.dobBtn.text = "Set birth date"
            }
        })

        binding.dobBtn.setOnClickListener {
            val c = Calendar.getInstance()
            val year = if (viewModel.dob.value != null) {
                viewModel.dob.value!!.year
            } else {
                c.get(Calendar.YEAR)
            }
            val month = if (viewModel.dob.value != null) {
                viewModel.dob.value!!.monthValue - 1
            } else {
                c.get(Calendar.MONTH)
            }
            val day = if (viewModel.dob.value != null) {
                viewModel.dob.value!!.dayOfMonth
            } else {
                c.get(Calendar.DAY_OF_MONTH)
            }

            val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { picker, year, month, day ->
                viewModel.setDOB(year, month + 1, day)
            }, year, month, day)

            c.add(Calendar.YEAR, -3)
            datePickerDialog.datePicker.maxDate = c.timeInMillis
            c.add(Calendar.YEAR, -7)
            datePickerDialog.datePicker.minDate = c.timeInMillis

            datePickerDialog.show()
        }

        return binding.root
    }

}