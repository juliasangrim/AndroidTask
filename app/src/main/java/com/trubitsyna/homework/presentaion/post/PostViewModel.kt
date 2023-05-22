package com.trubitsyna.homework.presentaion.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.trubitsyna.homework.base.BaseViewModel
import com.trubitsyna.homework.data.local.model.Post
import com.trubitsyna.homework.data.remote.model.LoadableResult
import com.trubitsyna.homework.domain.post.DeletePostUseCase
import com.trubitsyna.homework.domain.post.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : BaseViewModel() {

    private val _mutablePostLiveData = MutableLiveData<LoadableResult<Post>>()
    val postLiveData: LiveData<LoadableResult<Post>> = _mutablePostLiveData

    private val _mutableDeleteResultLiveData = MutableLiveData<LoadableResult<Boolean>>()
    val deleteResultLiveData: LiveData<LoadableResult<Boolean>> = _mutableDeleteResultLiveData

    fun getPost(postId: String) {
        _mutablePostLiveData.loadData {
            getPostUseCase.execute(postId)
        }
    }

    fun deletePost(postId: String) {
        _mutableDeleteResultLiveData.loadData {
            deletePostUseCase.execute(postId)
        }
    }
}