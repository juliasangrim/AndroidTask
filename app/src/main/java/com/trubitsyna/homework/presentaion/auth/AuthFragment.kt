package com.trubitsyna.homework.presentaion.auth

import android.os.Bundle
import android.view.View
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.trubitsyna.homework.R
import com.trubitsyna.homework.data.local.model.auth.CheckNameResponse
import com.trubitsyna.homework.data.remote.model.auth.AuthResult
import com.trubitsyna.homework.databinding.FragmentAuthBinding
import com.trubitsyna.homework.utils.clearError
import com.trubitsyna.homework.utils.disable
import com.trubitsyna.homework.utils.doOnApplyWindowInsets
import com.trubitsyna.homework.utils.enable
import com.trubitsyna.homework.utils.show
import com.trubitsyna.homework.utils.showError
import com.trubitsyna.homework.utils.showErrorWithAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {
    private val binding by viewBinding(FragmentAuthBinding::bind)
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.doOnApplyWindowInsets { view, insets, padding ->
            val bottomPadding = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            val topPadding = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            view.updatePadding(
                bottom = padding.bottom + bottomPadding
            )
            insets
        }
        with(binding) {
            buttonContinue.setOnClickListener {
                checkNameListener()
            }

            editTextUsernameInput.doOnTextChanged { _, _, _, _ ->
                textInputLayoutUsernameInput.clearError()
            }

            editTextPasswordInput.doOnTextChanged { _, _, _, _ ->
                textInputLayoutPasswordInput.clearError()
            }
        }

        viewModel.validationResultLiveData.observe(viewLifecycleOwner) { authResult ->
            when (authResult) {
                is AuthResult.Error -> {
                    binding.textInputLayoutUsernameInput.enable()
                    binding.root.showErrorWithAction(R.string.error_msg_unknown) {
                        checkNameListener()
                    }
                }

                is AuthResult.NetworkError -> {
                    binding.textInputLayoutUsernameInput.enable()
                    binding.root.showError(R.string.error_msg_network)
                }

                is AuthResult.PasswordError -> {/* no-op */
                }

                is AuthResult.Success -> {
                    onSuccessAuthResultUsername(authResult.data)
                }

                is AuthResult.Validate -> {
                    binding.textInputLayoutUsernameInput.disable()
                }
            }
        }

        viewModel.userLiveData.observe(viewLifecycleOwner) { authResult ->
            when (authResult) {
                is AuthResult.Error -> {
                    binding.textInputLayoutPasswordInput.enable()
                    binding.root.showError(R.string.error_msg_unknown)
                }

                is AuthResult.NetworkError -> {
                    binding.textInputLayoutPasswordInput.enable()
                    binding.root.showError(R.string.error_msg_network)
                }

                is AuthResult.PasswordError -> {
                    binding.textInputLayoutPasswordInput.enable()
                    binding.root.showError(R.string.error_msg_incorrect_password)
                }

                is AuthResult.Success -> {
                    authResult.data.also { userData ->
                        viewModel.saveUserData(userData)
                        val navController = findNavController()
                        val mainNavGraph =
                            navController.navInflater.inflate(R.navigation.nav_graph)
                        navController.graph = mainNavGraph
                    }
                }

                is AuthResult.Validate -> {
                    binding.textInputLayoutPasswordInput.disable()
                }
            }
        }
    }

    private fun checkNameListener() {
        viewModel.checkUsername(getUsername())
    }

    private fun checkPasswordListener(usernameValidationResponse: CheckNameResponse) {
        val username = getUsername()
        val password = getPassword()
        if (viewModel.isPasswordValid(getPassword())) {
            viewModel.auth(username, password, usernameValidationResponse)
        } else {
            binding.textInputLayoutPasswordInput.showError(
                getString(R.string.error_msg_short_password)
            )
        }
    }

    private fun onSuccessAuthResultUsername(response: CheckNameResponse) {
        with(binding) {
            when (response) {
                CheckNameResponse.SHORT_NAME -> {
                    textInputLayoutUsernameInput.enable()
                    textInputLayoutUsernameInput.showError(
                        getString(R.string.error_msg_short_username)
                    )
                }

                CheckNameResponse.LONG_NAME -> {
                    textInputLayoutUsernameInput.enable()
                    textInputLayoutUsernameInput.showError(
                        getString(R.string.error_msg_long_username)
                    )
                }

                CheckNameResponse.INVALID_CHAR_NAME -> {
                    textInputLayoutUsernameInput.enable()
                    textInputLayoutUsernameInput.showError(
                        getString(R.string.error_msg_invalid_char_username)
                    )
                }

                else -> {
                    textInputLayoutUsernameInput.clearError()
                    textInputLayoutPasswordInput.show()
                    buttonContinue.setOnClickListener {
                        checkPasswordListener(response)
                    }
                }
            }
        }
    }

    private fun getUsername() = binding.editTextUsernameInput.editableText.toString()

    private fun getPassword() = binding.editTextPasswordInput.editableText.toString()
}