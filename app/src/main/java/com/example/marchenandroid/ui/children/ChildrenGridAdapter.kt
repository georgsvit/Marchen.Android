package com.example.marchenandroid.ui.children

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marchenandroid.data.network.dto.responses.ChildResponse
import com.example.marchenandroid.databinding.ChildrenViewItemBinding

class ChildrenGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<ChildResponse, ChildrenGridAdapter.ChildrenViewHolder>(DiffCallback) {
    class ChildrenViewHolder(private var binding: ChildrenViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(child: ChildResponse) {
            binding.child = child
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (child: ChildResponse) -> Unit) {
        fun onClick(child: ChildResponse) = clickListener(child)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ChildResponse>() {
        override fun areItemsTheSame(oldItem: ChildResponse, newItem: ChildResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ChildResponse, newItem: ChildResponse): Boolean {
            return oldItem.Id == newItem.Id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildrenViewHolder {
        return ChildrenViewHolder(ChildrenViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ChildrenViewHolder, position: Int) {
        val child = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(child)
        }
        holder.bind(child)
    }
}