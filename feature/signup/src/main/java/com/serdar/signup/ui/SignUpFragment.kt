package com.serdar.signup.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.serdar.common.base.BaseFragment
import com.serdar.common.validation.EmptyTextRule
import com.serdar.common.validation.PasswordRule
import com.serdar.common.validation.validationRule
import com.serdar.signin.ui.SignInUiState
import com.serdar.signin.ui.SignInViewModel
import com.serdar.signup.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import com.serdar.navigation.R
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {
    private val viewModel by viewModels<SignUpViewModel>()
    private val viewModelSignIn by viewModels<SignInViewModel>()

    override fun observeUi() {
        super.observeUi()
        initObserveLogin()
        createUser()
        navigateUi()
    }

    private fun validateRule(): Boolean {
        val email = binding.editTxtEmail.validationRule(
            rules = listOf(EmptyTextRule())
        )
        val password = binding.editTxtPassword.validationRule(
            rules = listOf(EmptyTextRule(), PasswordRule())
        )
        return email && password
    }

    private fun initObserveLogin() {
        viewModel.viewModelScope.launch {
            viewModel.signUpUiState.collect {
                when (it) {
                    is SignUpUiState.Loading -> {

                    }

                    is SignUpUiState.Error -> {

                    }

                    is SignUpUiState.Success -> {
                        val emailText = binding.editTxtEmail.text.toString()
                        val passwordText = binding.editTxtPassword.text.toString()
                        viewModelSignIn.signInWithFirebaseAuth(emailText,passwordText)
                    }
                }
            }

        }
        viewModel.viewModelScope.launch {
            viewModelSignIn.signInUiState.collect(){
                when (it) {
                    is SignInUiState.Loading -> {

                    }

                    is SignInUiState.Error -> {

                    }

                    is SignInUiState.Success -> {
                       findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                    }
                }
            }
        }

    }

    private fun createUser() {
        binding.btnRegister.setOnClickListener {
            if (validateRule()) {
                val emailText = binding.editTxtEmail.text.toString()
                val passwordText = binding.editTxtPassword.text.toString()
                viewModel.signUpWithFirebaseAuth(emailText, passwordText)
            }
        }
    }
    private fun navigateUi(){
        binding.txtLoginAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }
}