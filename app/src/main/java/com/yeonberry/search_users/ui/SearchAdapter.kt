package com.yeonberry.search_users.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yeonberry.search_users.data.model.User
import com.yeonberry.search_users.databinding.ItemSearchBinding

class SearchAdapter(private val onItemClick: (User) -> Unit) :
    PagingDataAdapter<User, SearchViewHolder>(SearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

class SearchViewHolder(
    private val binding: ItemSearchBinding, val onItemClick: (User) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: User) {
        binding.apply {
            root.setOnClickListener {
                onItemClick(item)
            }
            Glide.with(ivImage)
                .load(item.imageUrl)
                .circleCrop()
                .into(ivImage)
            tvName.text = item.username
            tvUrl.text = item.url
        }
    }
}

private class SearchDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = false

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = false
}
