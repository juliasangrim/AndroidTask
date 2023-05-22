package com.trubitsyna.homework.data.mapper

import com.trubitsyna.homework.data.local.model.Profile
import com.trubitsyna.homework.data.remote.model.ApiProfile
import javax.inject.Inject

class ProfileMapper @Inject constructor(
    private val imageMapper: ImageMapper,
) {
    fun apiProfileToProfile(apiModel: ApiProfile): Profile =
        Profile(
            id = apiModel.id,
            username = apiModel.username,
            displayName = apiModel.displayName,
            bio = apiModel.bio,
            avatarId = apiModel.avatarId,
            avatarSmall = apiModel.avatarSmall,
            avatarLarge = apiModel.avatarLarge,
            subscribed = apiModel.subscribed,
            subscribersCount = apiModel.subscribersCount,
            postsCount = apiModel.postsCount,
            imagesCount = apiModel.imagesCount,
            images = apiModel.images.map {
                imageMapper.apiImageToImage(it)
            }
        )
}