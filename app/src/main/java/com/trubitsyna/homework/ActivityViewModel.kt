package com.trubitsyna.homework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trubitsyna.homework.domain.auth.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
) : ViewModel() {

    private val _mutableTokenLiveData = MutableLiveData<String?>()
    val tokenLiveData: LiveData<String?> = _mutableTokenLiveData

    fun getToken() {
        viewModelScope.launch {
            val token = getTokenUseCase.execute()
            _mutableTokenLiveData.postValue(token)
        }
    }

}