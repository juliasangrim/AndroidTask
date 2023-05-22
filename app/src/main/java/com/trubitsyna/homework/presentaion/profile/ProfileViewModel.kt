package com.trubitsyna.homework.presentaion.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.trubitsyna.homework.base.BaseViewModel
import com.trubitsyna.homework.data.local.model.Post
import com.trubitsyna.homework.data.local.model.Profile
import com.trubitsyna.homework.data.remote.model.LoadableResult
import com.trubitsyna.homework.domain.auth.GetUserIdUseCase
import com.trubitsyna.homework.domain.post.GetProfilePostsUseCase
import com.trubitsyna.homework.domain.profile.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getPostsUseCase: GetProfilePostsUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
) : BaseViewModel(
) {

    private val _mutableProfileLiveData = MutableLiveData<LoadableResult<Profile>>()
    val profileLiveData: LiveData<LoadableResult<Profile>> = _mutableProfileLiveData

    private val _mutablePostLiveData = MutableLiveData<PagingData<Post>>()
    val postLiveData: LiveData<PagingData<Post>> = _mutablePostLiveData

    private val _mutableUserIdLiveData = MutableLiveData<String?>()
    val userIdLivaData: LiveData<String?> = _mutableUserIdLiveData

    fun getUserId() {
        viewModelScope.launch {
            val result = getUserIdUseCase.execute()
            _mutableUserIdLiveData.postValue(result)
        }
    }

    fun getProfile(userId: String?) {
        userId?.let {
            _mutableProfileLiveData.loadData {
                getProfileUseCase.execute(it)
            }
        }
    }

    fun loadPosts(
        profileId: String?
    ) {
        viewModelScope.launch {
            profileId?.let { profileId ->
                getPostsUseCase.execute(
                    profileId
                )
                    .cachedIn(viewModelScope)
                    .collect {
                        _mutablePostLiveData.postValue(it)
                    }
            }
        }
    }
}