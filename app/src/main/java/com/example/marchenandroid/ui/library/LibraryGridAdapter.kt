package com.example.marchenandroid.ui.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marchenandroid.data.network.dto.responses.FairytaleGetResponse
import com.example.marchenandroid.databinding.LibraryViewItemBinding

class LibraryGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<FairytaleGetResponse, LibraryGridAdapter.FairytaleViewHolder>(DiffCallback) {
    class FairytaleViewHolder(private var binding: LibraryViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(fairytale: FairytaleGetResponse) {
            binding.fairytale = fairytale
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (fairytale:FairytaleGetResponse) -> Unit) {
        fun onClick(fairytale: FairytaleGetResponse) = clickListener(fairytale)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<FairytaleGetResponse>() {
        override fun areItemsTheSame(oldItem: FairytaleGetResponse, newItem: FairytaleGetResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FairytaleGetResponse, newItem: FairytaleGetResponse): Boolean {
            return oldItem.Id == newItem.Id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FairytaleViewHolder {
        return FairytaleViewHolder(LibraryViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FairytaleViewHolder, position: Int) {
        val fairytale = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(fairytale)
        }
        holder.bind(fairytale)
    }
}