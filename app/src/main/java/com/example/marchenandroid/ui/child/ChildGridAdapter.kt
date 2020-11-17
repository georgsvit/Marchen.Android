package com.example.marchenandroid.ui.child

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marchenandroid.data.network.dto.responses.ChildReportResponse
import com.example.marchenandroid.databinding.ChildViewItemBinding

class ChildGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<ChildReportResponse, ChildGridAdapter.ChildReportViewHolder>(DiffCallback) {
    class ChildReportViewHolder(private var binding: ChildViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(report: ChildReportResponse) {
            binding.report = report
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (report: ChildReportResponse) -> Unit) {
        fun onClick(report: ChildReportResponse) = clickListener(report)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ChildReportResponse>() {
        override fun areItemsTheSame(oldItem: ChildReportResponse, newItem: ChildReportResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ChildReportResponse, newItem: ChildReportResponse): Boolean {
            return oldItem.Id == newItem.Id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildReportViewHolder {
        return ChildReportViewHolder(ChildViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ChildReportViewHolder, position: Int) {
        val report = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(report)
        }
        holder.bind(report)
    }
}