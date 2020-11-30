package com.example.marchenandroid.ui.child_form

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marchenandroid.data.network.dto.responses.AvatarResponse
import com.example.marchenandroid.databinding.AvatarViewItemBinding

class AvatarsGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<AvatarResponse, AvatarsGridAdapter.AvatarViewHolder>(DiffCallback) {
    class AvatarViewHolder(private var binding: AvatarViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(avatar: AvatarResponse) {
            binding.avatar = avatar
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (avatar: AvatarResponse) -> Unit) {
        fun onClick(avatar: AvatarResponse) = clickListener(avatar)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<AvatarResponse>() {
        override fun areItemsTheSame(oldItem: AvatarResponse, newItem: AvatarResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AvatarResponse, newItem: AvatarResponse): Boolean {
            return oldItem.AvatarURL == newItem.AvatarURL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarsGridAdapter.AvatarViewHolder {
        return AvatarViewHolder(AvatarViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
        val report = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(report)
        }
        holder.bind(report)
    }
}