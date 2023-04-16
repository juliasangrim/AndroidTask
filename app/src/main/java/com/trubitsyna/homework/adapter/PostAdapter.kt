package com.trubitsyna.homework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.trubitsyna.homework.data.PostData
import com.trubitsyna.homework.databinding.ViewCardPostBinding
import com.trubitsyna.homework.diffcallback.PostDiffCallback
import com.trubitsyna.homework.utils.Constants
import java.text.SimpleDateFormat

class PostAdapter : ListAdapter<PostData, PostAdapter.PostViewHolder>(
    PostDiffCallback()
) {

    private lateinit var onClick: (PostData) -> Unit

    fun setCallback(callback: (PostData) -> Unit) {
        onClick = callback
    }

    inner class PostViewHolder(
        private val binding: ViewCardPostBinding
    ) : ViewHolder(binding.root) {

        fun bind(item: PostData) {
            with(binding.layoutViewPost) {
                textViewName.text = item.name
                val dateFormatter = SimpleDateFormat(Constants.DATE_PATTERN)
                textViewDate.text = dateFormatter.format(item.date)
                textViewPost.text = item.mainText
                imageViewPost.load(item.imageUrl)
                buttonLike.text = item.likeCount.toString()
                root.setOnClickListener {
                    onClick.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ViewCardPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}