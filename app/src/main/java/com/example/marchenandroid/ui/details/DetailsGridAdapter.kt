package com.example.marchenandroid.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marchenandroid.data.network.dto.responses.SavepointResponse
import com.example.marchenandroid.databinding.DetailsViewItemBinding

class DetailsGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<SavepointResponse, DetailsGridAdapter.SavepointViewHolder>(DiffCallback) {
    class SavepointViewHolder(private var binding: DetailsViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(save: SavepointResponse) {
            binding.save = save
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (save: SavepointResponse) -> Unit) {
        fun onClick(save: SavepointResponse) = clickListener(save)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<SavepointResponse>() {
        override fun areItemsTheSame(oldItem: SavepointResponse, newItem: SavepointResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SavepointResponse, newItem: SavepointResponse): Boolean {
            return oldItem.ChildId == newItem.ChildId && oldItem.UnitId == newItem.UnitId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavepointViewHolder {
        return SavepointViewHolder(DetailsViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SavepointViewHolder, position: Int) {
        val fairytale = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(fairytale)
        }
        holder.bind(fairytale)
    }
}