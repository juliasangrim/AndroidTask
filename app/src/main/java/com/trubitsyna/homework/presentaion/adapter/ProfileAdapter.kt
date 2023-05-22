package com.trubitsyna.homework.presentaion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.trubitsyna.homework.data.local.model.Profile
import com.trubitsyna.homework.databinding.ViewCardProfileBinding
import javax.inject.Inject

class ProfileAdapter @Inject constructor() :
    Adapter<ProfileAdapter.ProfileViewHolder>() {

    companion object {
        private const val MAX_ITEM_COUNT = 1
    }

    private var profileData: Profile? = null

    fun setItem(item: Profile) {
        profileData = item
        notifyDataSetChanged()
    }

    inner class ProfileViewHolder(
        private var binding: ViewCardProfileBinding
    ) : ViewHolder(binding.root) {
        fun bind(item: Profile?) {
            with(binding) {
                textViewName.text = item?.displayName ?: item?.username
                textViewProfileSubtext.text = item?.bio
                textViewImageCounter.text = item?.imagesCount.toString()
                textViewSubscribeCounter.text = item?.subscribersCount.toString()
                textViewPostCounter.text = item?.postsCount.toString()
                imageViewProfile.load(item?.avatarSmall)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ViewCardProfileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProfileViewHolder(binding)
    }

    override fun getItemCount(): Int = if (profileData != null) MAX_ITEM_COUNT else 0

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(profileData)
    }
}