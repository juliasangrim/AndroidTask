package com.trubitsyna.homework.presentaion.diffCallback

import androidx.recyclerview.widget.DiffUtil
import com.trubitsyna.homework.data.local.model.Post

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}