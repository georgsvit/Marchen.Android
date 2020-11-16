package com.example.marchenandroid.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.data.network.dto.responses.ChildResponse
import com.example.marchenandroid.data.network.dto.responses.FairytaleGetResponse
import com.example.marchenandroid.ui.children.ChildrenGridAdapter
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