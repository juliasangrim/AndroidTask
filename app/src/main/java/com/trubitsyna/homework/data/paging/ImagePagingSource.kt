package com.trubitsyna.homework.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.trubitsyna.homework.data.remote.NanoPostApiService
import com.trubitsyna.homework.data.remote.model.ApiImage

class ImagePagingSource constructor(
    private val profileId: String,
    private val apiService: NanoPostApiService,
) : PagingSource<String, ApiImage>() {
    override fun getRefreshKey(
        state: PagingState<String, ApiImage>
    ): String? {
        return null
    }

    override suspend fun load(
        params: LoadParams<String>
    ): LoadResult<String, ApiImage> {
        return try {
            val response = apiService.getProfileImages(
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