package com.trubitsyna.homework.presentaion.image_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.trubitsyna.homework.data.local.model.Image
import com.trubitsyna.homework.domain.auth.GetUserIdUseCase
import com.trubitsyna.homework.domain.image.GetProfileImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val getProfileImagesUseCase: GetProfileImagesUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
) : ViewModel() {

    private val _mutableImageListLiveData = MutableLiveData<PagingData<Image>>()
    val imageListLiveData: LiveData<PagingData<Image>> = _mutableImageListLiveData

    private val _mutableUserIdLiveData = MutableLiveData<String?>()
    val userIdLiveData: LiveData<String?> = _mutableUserIdLiveData

    fun loadImages(profileId: String) {
        viewModelScope.launch {
            getProfileImagesUseCase.execute(profileId)
                .cachedIn(viewModelScope)
                .collect {
                    _mutableImageListLiveData.postValue(it)
                }
        }
    }

    fun getUserId() {
        viewModelScope.launch {
            val result = getUserIdUseCase.execute()
            _mutableUserIdLiveData.postValue(result)
        }
    }
}