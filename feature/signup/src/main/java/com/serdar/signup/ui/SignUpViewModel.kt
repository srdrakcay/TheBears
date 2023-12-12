package com.serdar.signup.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serdar.common.response.FirebaseAuthResponse
import com.serdar.signup.R
import com.serdar.signup.domain.SignUpWithEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpWithEmailUseCase: SignUpWithEmailUseCase) :
    ViewModel() {

    private val _signUpUiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Loading)
    val signInUiState: MutableStateFlow<SignUpUiState> = _signUpUiState


    fun signInWithFirebaseAuth(email: String, password: String) {
        viewModelScope.launch {
            signUpWithEmailUseCase(email, password).onEach {
                when (it) {
                    is FirebaseAuthResponse.Error -> {
                        _signUpUiState.value = (SignUpUiState.Error(R.string.error))
                    }

                    FirebaseAuthResponse.Loading -> {
                        _signUpUiState.value = (SignUpUiState.Loading)
                    }

                    is FirebaseAuthResponse.Success -> {
                        _signUpUiState.value =
                            (SignUpUiState.Success(SignUpUiState.Success(it.result!!)))
                    }
                }
            }.launchIn(viewModelScope)

        }

    }
}