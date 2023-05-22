package com.trubitsyna.homework.presentaion.diffCallback

import androidx.recyclerview.widget.DiffUtil
import com.trubitsyna.homework.data.local.model.Image

class ImageDiffCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }
}