package com.example.marchenandroid.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.data.network.dto.responses.ChildReportResponse
import com.example.marchenandroid.data.network.dto.responses.ChildResponse
import com.example.marchenandroid.data.network.dto.responses.FairytaleGetResponse
import com.example.marchenandroid.data.network.dto.responses.SavepointResponse
import com.example.marchenandroid.ui.child.ChildGridAdapter
import com.example.marchenandroid.ui.children.ChildrenGridAdapter
import com.example.marchenandroid.ui.details.DetailsGridAdapter
import com.example.marchenandroid.ui.library.LibraryGridAdapter

@BindingAdapter("childrenListData")
fun bindChildrenRecyclerView(recyclerView: RecyclerView, data: List<ChildResponse>?) {
    val adapter = recyclerView.adapter as ChildrenGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("fairytaleListData")
fun bindFairytaleRecyclerView(recyclerView: RecyclerView, data: List<FairytaleGetResponse>?) {
    val adapter = recyclerView.adapter as LibraryGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("savepointsListData")
fun bindSavepointsRecyclerView(recyclerView: RecyclerView, data: List<SavepointResponse>?) {
    val adapter = recyclerView.adapter as DetailsGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("reportsListData")
fun bindReportsRecyclerView(recyclerView: RecyclerView, data: List<ChildReportResponse>?) {
    val adapter = recyclerView.adapter as ChildGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("apiStatus")
fun bindStatus(view: View, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            view.visibility = View.VISIBLE
        }
        ApiStatus.ERROR -> {
            view.visibility = View.VISIBLE
        }
        ApiStatus.DONE -> {
            view.visibility = View.GONE
        }
    }
}