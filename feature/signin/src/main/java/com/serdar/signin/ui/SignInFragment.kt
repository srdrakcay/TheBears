package com.serdar.signin.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.serdar.common.base.BaseFragment
import com.serdar.common.validation.EmptyTextRule
import com.serdar.common.validation.PasswordRule
import com.serdar.common.validation.validationRule
import com.serdar.navigation.R
import com.serdar.signin.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {
    private lateinit var firebaseAuth: FirebaseAuth
    private val viewModel by viewModels<SignInViewModel>()

    override fun observeUi() {
        super.observeUi()
        setCurrentUser()
        initObserveLogin()
        login()
        navigateUi()
    }

    private fun setCurrentUser() {
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
        }
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
            viewModel.signInUiState.collect {
                when (it) {
                    is SignInUiState.Loading -> {

                    }

                    is SignInUiState.Error -> {

                    }

                    is SignInUiState.Success -> {
                        findNavController().navigate(R.id.action_signInFragment_to_homeFragment)

                    }
                }
            }
        }

    }

    private fun login() {
        binding.btnLogIn.setOnClickListener {
            if (validateRule()) {
                val emailText = binding.editTxtEmail.text.toString()
                val passwordText = binding.editTxtPassword.text.toString()
                viewModel.signInWithFirebaseAuth(emailText, passwordText)
            }
        }
    }
    private fun navigateUi(){
        binding.txtCreateNewAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }
}