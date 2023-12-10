package com.serdar.signup.ui

import androidx.annotation.StringRes

sealed class SignUpUiState {
    data object Loading : SignUpUiState()
    data class Success(val data: Any) : SignUpUiState()
    data class Error(@StringRes val message: Int) : SignUpUiState()
}