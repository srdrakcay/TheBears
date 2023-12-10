package com.serdar.signin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serdar.common.FirebaseAuthResponse
import com.serdar.signin.R
import com.serdar.signin.domain.FirebaseCreateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val createUserUseCase: FirebaseCreateUserUseCase

) : ViewModel() {

    private val _signInUiState = MutableStateFlow<SignInUiState>(SignInUiState.Loading)
    val signInUiState: MutableStateFlow<SignInUiState> = _signInUiState
    fun signInWithFirebaseAuth(email: String, password: String) {
        viewModelScope.launch {
            createUserUseCase(email, password).onEach {
                when (it) {
                    is FirebaseAuthResponse.Error -> {
                        _signInUiState.value = (SignInUiState.Error(R.string.error))
                    }

                    FirebaseAuthResponse.Loading -> {
                        _signInUiState.value = (SignInUiState.Loading)
                    }

                    is FirebaseAuthResponse.Success -> {
                        _signInUiState.value =
                            (SignInUiState.Success(SignInUiState.Success(it.result!!)))
                    }
                }
            }.launchIn(viewModelScope)

        }

    }
}