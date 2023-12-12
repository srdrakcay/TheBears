package com.serdar.profile.ui

sealed class SignOutState {
    data object Idle : SignOutState()
    data object Loading : SignOutState()
    data object Success : SignOutState()
    data class Error(val errorMessage: String) : SignOutState()
}
