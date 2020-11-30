package com.example.marchenandroid.util

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.marchenandroid.R
import com.example.marchenandroid.data.network.ApiStatus
import com.example.marchenandroid.data.network.dto.responses.*
import com.example.marchenandroid.ui.awards.AwardsGridAdapter
import com.example.marchenandroid.ui.child_form.AvatarsGridAdapter
import com.example.marchenandroid.ui.children.ChildrenGridAdapter
import com.example.marchenandroid.ui.details.DetailsGridAdapter
import com.example.marchenandroid.ui.library.LibraryGridAdapter
import com.example.marchenandroid.ui.reports.ReportsGridAdapter

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
    val adapter = recyclerView.adapter as ReportsGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("awardsListData")
fun bindAwardsRecyclerView(recyclerView: RecyclerView, data: List<AwardResponse>?) {
    val adapter = recyclerView.adapter as AwardsGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("avatarsListData")
fun bindAvatarsRecyclerView(recyclerView: RecyclerView, data: List<AvatarResponse>?) {
    val adapter = recyclerView.adapter as AvatarsGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
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