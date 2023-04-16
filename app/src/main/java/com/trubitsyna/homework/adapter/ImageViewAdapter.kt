package com.trubitsyna.homework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.trubitsyna.homework.data.ImageData
import com.trubitsyna.homework.databinding.ViewImageItemBinding
import com.trubitsyna.homework.diffcallback.ImageDiffCallback

class ImageViewAdapter : ListAdapter<ImageData, ImageViewAdapter.ImageViewHolder>(
    ImageDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ViewImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ImageViewHolder(
        private val binding: ViewImageItemBinding
    ) : ViewHolder(binding.root) {
        fun bind(item: ImageData) {
            with(binding) {
                imageViewItem.load(item.imageUrl)
            }
        }
    }
}