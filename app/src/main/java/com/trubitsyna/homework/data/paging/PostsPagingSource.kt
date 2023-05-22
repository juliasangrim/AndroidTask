package com.trubitsyna.homework.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.trubitsyna.homework.data.remote.NanoPostApiService
import com.trubitsyna.homework.data.remote.model.ApiPost

class PostsPagingSource constructor(
    private val profileId: String,
    private val apiService: NanoPostApiService,
) : PagingSource<String, ApiPost>() {
    override fun getRefreshKey(
        state: PagingState<String, ApiPost>
    ): String? {
        return null
    }

    override suspend fun load(
        params: LoadParams<String>
    ): LoadResult<String, ApiPost> {
        return try {
            val response = apiService.getProfilePosts(
                profileId = profileId,
                count = params.loadSize,
                offset = params.key
            )
            LoadResult.Page(
                data = response.items,
                nextKey = response.offset,
                prevKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}