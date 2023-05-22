package com.trubitsyna.homework.presentaion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.trubitsyna.homework.data.local.model.Image
import com.trubitsyna.homework.databinding.ViewImagePostItemBinding
import com.trubitsyna.homework.presentaion.diffCallback.ImageDiffCallback
import com.trubitsyna.homework.utils.getMaxSizeImageUrl
import javax.inject.Inject

class ImageViewAdapter @Inject constructor() :
    ListAdapter<Image, ImageViewAdapter.ImageViewHolder>(
        ImageDiffCallback()
    ) {

    private lateinit var onImageClick: (item: Image) -> Unit

    fun setCallback(callback: (item: Image) -> Unit) {
        onImageClick = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ViewImagePostItemBinding.inflate(
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
        private val binding: ViewImagePostItemBinding
    ) : ViewHolder(binding.root) {
        fun bind(item: Image) {
            with(binding) {
                imageViewItem.load(item.getMaxSizeImageUrl())
                imageViewItem.setOnClickListener {
                    onImageClick(item)
                }
            }
        }
    }
}