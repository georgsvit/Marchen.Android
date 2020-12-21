package com.example.marchenandroid.ui.library

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.SpannableStringBuilder
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.marchenandroid.R
import com.example.marchenandroid.databinding.FragmentLibraryBinding
import com.example.marchenandroid.ui.details.DetailsActivity
import org.w3c.dom.Text
import java.util.*

class LibraryFragment : Fragment() {

    private val viewModel: LibraryViewModel by lazy {
        ViewModelProvider(this).get(LibraryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentLibraryBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.libraryViewModel = viewModel
        binding.talesGrid.adapter = LibraryGridAdapter(LibraryGridAdapter.OnClickListener { viewModel.displayFairytaleDetails(it) })
        viewModel.navigateToSelectedFairytale.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                viewModel.saveSelectedFairytaleToSP(it)
                startActivity(Intent(context, DetailsActivity::class.java))
                viewModel.displayFairytaleDetailsComplete()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun filter() {
        val filterDialog: AlertDialog.Builder? = activity?.let {
            val cs = viewModel.types.toTypedArray<CharSequence>()

            val builder = AlertDialog.Builder(it)

            val layout: LinearLayout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL

            val minAgeInput: EditText = EditText(context)
            minAgeInput.inputType = InputType.TYPE_CLASS_NUMBER
            minAgeInput.setRawInputType(Configuration.KEYBOARD_12KEY)
            minAgeInput.hint = "Minimum age"
            minAgeInput.text = SpannableStringBuilder(viewModel.minAge.toString())

            val maxAgeInput: EditText = EditText(context)
            maxAgeInput.inputType = InputType.TYPE_CLASS_NUMBER
            maxAgeInput.setRawInputType(Configuration.KEYBOARD_12KEY)
            maxAgeInput.hint = "Maximum age"
            maxAgeInput.text = SpannableStringBuilder(viewModel.maxAge.toString())

            val minAgeLabel = TextView(context)
            minAgeLabel.text = "Minimum age"

            val maxAgeLabel = TextView(context)
            maxAgeLabel.text = "Maximum age"

            layout.addView(minAgeLabel)
            layout.addView(minAgeInput)
            layout.addView(maxAgeLabel)
            layout.addView(maxAgeInput)

            builder.setView(layout)

            builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                if (maxAgeInput.text.toString() != "") {
                    viewModel.maxAge = Integer.parseInt(maxAgeInput.text.toString())
                }
                if (minAgeInput.text.toString() != "") {
                    viewModel.minAge = Integer.parseInt(minAgeInput.text.toString())
                }
                viewModel.getFilteredFairyTales()
            })
            builder.setNegativeButton("Reset", DialogInterface.OnClickListener { dialog, id ->
                viewModel.maxAge = 10
                viewModel.minAge = 0
                viewModel.selectedItem = -1
                viewModel.getFilteredFairyTales()
            })
            builder.setNeutralButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
            })

            builder.setSingleChoiceItems(cs, viewModel.selectedItem, DialogInterface.OnClickListener { dialog, id ->
                viewModel.selectedItem = id
            })

            builder.setTitle("Select filtering options")
        }

        val dialog: AlertDialog? = filterDialog?.create()
        dialog?.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.library_menu, menu)

        if (viewModel == null) {
            menu.findItem(R.id.filter)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> filter()
        }
        return super.onOptionsItemSelected(item)
    }
}