package com.trubitsyna.homework.presentaion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.trubitsyna.homework.data.local.model.Image
import com.trubitsyna.homework.databinding.ViewImageItemBinding
import com.trubitsyna.homework.presentaion.diffCallback.ImageDiffCallback
import com.trubitsyna.homework.utils.getMinSizeImageUrl
import javax.inject.Inject

class PagingImageViewAdapter @Inject constructor() :
    PagingDataAdapter<Image, PagingImageViewAdapter.ImageViewHolder>(
        ImageDiffCallback()
    ) {

    private lateinit var onImageClick: (item: Image) -> Unit

    fun setCallback(callback: (item: Image) -> Unit) {
        onImageClick = callback
    }

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
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ImageViewHolder(
        private val binding: ViewImageItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Image) {
            with(binding) {
                imageViewItem.load(item.getMinSizeImageUrl())
                imageViewItem.setOnClickListener {
                    onImageClick(item)
                }
            }
        }
    }
}