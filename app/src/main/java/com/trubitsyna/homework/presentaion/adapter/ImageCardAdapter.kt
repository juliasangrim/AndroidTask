package com.trubitsyna.homework.presentaion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.trubitsyna.homework.data.local.model.Image
import com.trubitsyna.homework.databinding.ViewCardImagesBinding
import com.trubitsyna.homework.utils.getMinSizeImageUrl
import javax.inject.Inject

class ImageCardAdapter @Inject constructor() :
    RecyclerView.Adapter<ImageCardAdapter.ImageViewHolder>() {

    companion object {
        private const val MAX_ITEM_COUNT = 1
    }

    private var imageCardData: List<Image>? = null

    private lateinit var onClick: () -> Unit

    fun setCallback(callback: () -> Unit) {
        onClick = callback
    }

    fun setItem(item: List<Image>) {
        imageCardData = item
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(
        private val binding: ViewCardImagesBinding
    ) : ViewHolder(binding.root) {
        fun bind(item: List<Image>?) {
            with(binding) {
                imageViewFirst.load(item?.getOrNull(0)?.getMinSizeImageUrl())
                imageViewSecond.load(item?.getOrNull(1)?.getMinSizeImageUrl())
                imageViewThird.load(item?.getOrNull(2)?.getMinSizeImageUrl())
                imageViewFourth.load(item?.getOrNull(3)?.getMinSizeImageUrl())
                binding.imageButtonNext.setOnClickListener {
                    onClick()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ViewCardImagesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int = if (imageCardData != null) MAX_ITEM_COUNT else 0

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageCardData)
    }
}