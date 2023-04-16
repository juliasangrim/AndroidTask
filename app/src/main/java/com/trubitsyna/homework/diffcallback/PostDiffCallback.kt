package com.trubitsyna.homework.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.trubitsyna.homework.data.PostData

class PostDiffCallback: DiffUtil.ItemCallback<PostData>() {
    override fun areItemsTheSame(oldItem: PostData, newItem: PostData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostData, newItem: PostData): Boolean {
        return oldItem == newItem
    }
}