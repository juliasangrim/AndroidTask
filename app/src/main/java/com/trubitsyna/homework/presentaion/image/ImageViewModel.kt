package com.trubitsyna.homework.presentaion.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.trubitsyna.homework.base.BaseViewModel
import com.trubitsyna.homework.data.local.model.Image
import com.trubitsyna.homework.data.remote.model.LoadableResult
import com.trubitsyna.homework.domain.image.DeleteImageUseCase
import com.trubitsyna.homework.domain.image.GetImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val getImageUseCase: GetImageUseCase,
    private val deleteImageUseCase: DeleteImageUseCase,
) : BaseViewModel() {

    private val _mutableImageLiveData = MutableLiveData<LoadableResult<Image>>()
    val imageLiveData: LiveData<LoadableResult<Image>> = _mutableImageLiveData

    private val _mutableDeleteResultLiveData = MutableLiveData<LoadableResult<Boolean>>()
    val deleteResultLiveData: LiveData<LoadableResult<Boolean>> = _mutableDeleteResultLiveData

    fun getImage(imageId: String) {
        _mutableImageLiveData.loadData {
            getImageUseCase.execute(imageId)
        }
    }

    fun deleteImage(imageId: String) {
        _mutableDeleteResultLiveData.loadData {
            deleteImageUseCase.execute(imageId)
        }
    }
}