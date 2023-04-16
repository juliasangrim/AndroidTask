package com.trubitsyna.homework.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.trubitsyna.homework.data.ImageData

class ImageDiffCallback: DiffUtil.ItemCallback<ImageData>() {
    override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
        return oldItem == newItem
    }
}