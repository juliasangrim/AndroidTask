package com.trubitsyna.homework.presentaion.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.trubitsyna.homework.base.AuthBaseViewModel
import com.trubitsyna.homework.data.local.model.auth.CheckNameResponse
import com.trubitsyna.homework.data.local.model.auth.UserData
import com.trubitsyna.homework.data.remote.model.auth.AuthResult
import com.trubitsyna.homework.domain.auth.CheckUsernameUseCase
import com.trubitsyna.homework.domain.auth.LoginUseCase
import com.trubitsyna.homework.domain.auth.RegisterUseCase
import com.trubitsyna.homework.domain.auth.SaveUserInfoUseCase
import com.trubitsyna.homework.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val checkUsernameUseCase: CheckUsernameUseCase,
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val saveUserInfoUseCase: SaveUserInfoUseCase,
) : AuthBaseViewModel() {

    private val _mutableValidationResultLiveData = MutableLiveData<AuthResult<CheckNameResponse>>()
    val validationResultLiveData: LiveData<AuthResult<CheckNameResponse>> =
        _mutableValidationResultLiveData

    private val _mutableUserLiveData = MutableLiveData<AuthResult<UserData>>()
    val userLiveData: LiveData<AuthResult<UserData>> = _mutableUserLiveData

    fun checkUsername(username: String) {
        _mutableValidationResultLiveData.loadData {
            checkUsernameUseCase.execute(username)
        }
    }

    fun isPasswordValid(password: String) =
        password.length >= Constants.VALID_LENGTH_PASSWORD

    fun auth(username: String, password: String, usernameValidationResponse: CheckNameResponse) {
        when (usernameValidationResponse) {
            CheckNameResponse.TAKEN -> {
                login(username, password)
            }

            CheckNameResponse.FREE -> {
                register(username, password)
            }

            else -> {/* no-op */
            }
        }
    }

    private fun login(username: String, password: String) {
        _mutableUserLiveData.loadData {
            loginUseCase.execute(username, password)
        }
    }

    private fun register(username: String, password: String) {
        _mutableUserLiveData.loadData {
            registerUseCase.execute(username, password)

        }
    }

    fun saveUserData(userData: UserData) {
        viewModelScope.launch {
            saveUserInfoUseCase.execute(userData)
        }
    }

}