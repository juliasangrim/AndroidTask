package com.trubitsyna.homework.presentaion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.trubitsyna.homework.data.local.model.Post
import com.trubitsyna.homework.databinding.ViewCardPostBinding
import com.trubitsyna.homework.presentaion.diffCallback.PostDiffCallback
import com.trubitsyna.homework.utils.getMaxSizeImageUrl
import java.text.SimpleDateFormat
import javax.inject.Inject

class PostAdapter @Inject constructor(
    val dateFormatter: SimpleDateFormat
) : PagingDataAdapter<Post, PostAdapter.PostViewHolder>(
    PostDiffCallback()
) {

    private lateinit var onClick: (Post) -> Unit

    fun setCallback(callback: (Post) -> Unit) {
        onClick = callback
    }

    inner class PostViewHolder(
        private val binding: ViewCardPostBinding
    ) : ViewHolder(binding.root) {

        fun bind(item: Post) {
            with(binding) {
                textViewName.text = item.owner.displayName ?: item.owner.username
                textViewDate.text = dateFormatter.format(item.dateCreated)
                item.text?.let {
                    textViewPost.text = item.text
                }
                imageViewProfile.load(item.owner.avatarUrl)
                imageViewPost.load(item.images.getOrNull(0)?.getMaxSizeImageUrl())
                buttonLike.text = item.likeCount.toString()
                root.setOnClickListener {
                    onClick(item)
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
        getItem(position)?.let { holder.bind(it) }
    }
}