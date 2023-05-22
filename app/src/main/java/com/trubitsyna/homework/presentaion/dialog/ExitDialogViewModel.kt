package com.trubitsyna.homework.presentaion.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trubitsyna.homework.domain.auth.DeleteUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExitDialogViewModel @Inject constructor(
    private val deleteUserInfoUseCase: DeleteUserInfoUseCase,
) : ViewModel() {

    fun deleteUserData() {
        viewModelScope.launch {
            deleteUserInfoUseCase.execute()
        }
    }
}