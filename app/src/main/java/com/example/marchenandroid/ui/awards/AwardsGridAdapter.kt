package com.example.marchenandroid.ui.awards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marchenandroid.data.network.dto.responses.AwardResponse
import com.example.marchenandroid.databinding.AwardsViewItemBinding

class AwardsGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<AwardResponse, AwardsGridAdapter.AwardViewHolder>(DiffCallback) {
    class AwardViewHolder(private var binding: AwardsViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(award: AwardResponse) {
            binding.award = award
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (award: AwardResponse) -> Unit) {
        fun onClick(award: AwardResponse) = clickListener(award)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<AwardResponse>() {
        override fun areItemsTheSame(oldItem: AwardResponse, newItem: AwardResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AwardResponse, newItem: AwardResponse): Boolean {
            return oldItem.AwardURL == newItem.AwardURL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AwardViewHolder {
        return AwardViewHolder(AwardsViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AwardViewHolder, position: Int) {
        val report = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(report)
        }
        holder.bind(report)
    }
}