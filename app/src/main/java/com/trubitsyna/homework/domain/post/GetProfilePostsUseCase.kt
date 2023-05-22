package com.trubitsyna.homework.domain.post

import androidx.paging.PagingData
import com.trubitsyna.homework.data.local.model.Post
import com.trubitsyna.homework.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfilePostsUseCase @Inject constructor(
    private val postRepository: PostRepository,
) {
    fun execute(
        profileId: String
    ): Flow<PagingData<Post>> {
        return postRepository.getProfilePosts(
            profileId
        )
    }
}