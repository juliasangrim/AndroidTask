package com.trubitsyna.homework.presentaion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.trubitsyna.homework.data.local.model.ImageUri
import com.trubitsyna.homework.databinding.ViewImageItemWithDeleteBinding
import com.trubitsyna.homework.presentaion.diffCallback.ImageUriDiffCallback
import javax.inject.Inject

class ImageCreatePostViewAdapter @Inject constructor() :
    ListAdapter<ImageUri, ImageCreatePostViewAdapter.ImageViewHolder>(
        ImageUriDiffCallback()
    ) {

    private lateinit var onDelete: (item: ImageUri) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ViewImageItemWithDeleteBinding.inflate(
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
        private val binding: ViewImageItemWithDeleteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageUri) {
            with(binding) {
                imageViewAddImage.load(item.uri)
                binding.imageViewCancelButton.setOnClickListener {
                    removeListItem(item)
                }
            }
        }
    }

    fun removeListItem(item: ImageUri) {
        val mutableList: MutableList<ImageUri> = currentList.toMutableList()
        mutableList.remove(item)
        submitList(mutableList)
    }
}