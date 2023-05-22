package com.trubitsyna.homework.presentaion.diffCallback

import androidx.recyclerview.widget.DiffUtil
import com.trubitsyna.homework.data.local.model.ImageUri

class ImageUriDiffCallback : DiffUtil.ItemCallback<ImageUri>() {
    override fun areItemsTheSame(oldItem: ImageUri, newItem: ImageUri): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageUri, newItem: ImageUri): Boolean {
        return oldItem == newItem
    }
}