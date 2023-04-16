package com.trubitsyna.homework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.trubitsyna.homework.data.ImageCardData
import com.trubitsyna.homework.databinding.ViewCardImagesBinding

class ImageCardAdapter(
    private var imageCardData: ImageCardData? = null
): RecyclerView.Adapter<ImageCardAdapter.ImageViewHolder>() {

    companion object {
        private const val MAX_ITEM_COUNT = 1
    }

    private lateinit var onClick: (List<String>?) -> Unit

    fun setCallback(callback: (List<String>?) -> Unit) {
        onClick = callback
    }

    fun setItem(item: ImageCardData) {
        imageCardData = item
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(
        private val binding: ViewCardImagesBinding
    ) : ViewHolder(binding.root) {
        fun bind(item: ImageCardData?) {
            with(binding) {
                imageViewFirst.load(item?.listImagesUrl?.get(0))
                imageViewSecond.load(item?.listImagesUrl?.get(1))
                imageViewThird.load(item?.listImagesUrl?.get(2))
                imageViewFourth.load(item?.listImagesUrl?.get(3))
                root.setOnClickListener {
                    onClick(item?.listImagesUrl)
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

    override fun getItemCount(): Int = MAX_ITEM_COUNT

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageCardData)
    }
}