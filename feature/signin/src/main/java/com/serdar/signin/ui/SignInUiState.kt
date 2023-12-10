package com.serdar.signin.ui

import androidx.annotation.StringRes

sealed class SignInUiState {
    data object Loading : SignInUiState()
    data class Success(val data: Any) : SignInUiState()
    data class Error(@StringRes val message: Int) : SignInUiState()
}