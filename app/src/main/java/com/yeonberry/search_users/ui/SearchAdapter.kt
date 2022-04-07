package com.yeonberry.search_users.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yeonberry.search_users.data.model.User
import com.yeonberry.search_users.databinding.ItemSearchBinding

class SearchAdapter(private val onItemClick: (User) -> Unit) :
    ListAdapter<User, RecyclerView.ViewHolder>(SearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? SearchViewHolder)?.bind(getItem(position))
    }

    class SearchViewHolder(
        private val binding: ItemSearchBinding, val onItemClick: (User) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.root.setOnClickListener {
                onItemClick(item)
            }
            Glide.with(binding.ivImage)
                .load(item.imageUrl)
                .circleCrop()
                .into(binding.ivImage)
            binding.tvName.text = item.username
            binding.tvUrl.text = item.url
        }
    }
}

private class SearchDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}
