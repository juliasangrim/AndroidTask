package com.trubitsyna.homework.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trubitsyna.homework.data.remote.model.LoadableResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

abstract class BaseViewModel() : ViewModel() {

    fun <T> MutableLiveData<LoadableResult<T>>.loadData(
        block: suspend () -> T,
    ) {
        viewModelScope.launch {
            flow {
                try {
                    emit(LoadableResult.Loading())
                    emit(LoadableResult.Success(block()))
                } catch (t: Throwable) {
                    emit(LoadableResult.Error(t))
                }
            }.collect { result ->
                postValue(result)

            }
        }
    }


}