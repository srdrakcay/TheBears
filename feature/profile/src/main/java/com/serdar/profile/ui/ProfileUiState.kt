package com.serdar.profile.ui

import com.serdar.profile.data.dto.CryptoPriceResponse
import java.lang.Exception

sealed class ProfileUiState {
    data object Loading : ProfileUiState()
    data class Success(val data: CryptoPriceResponse) : ProfileUiState()
    data class Error( val message: Exception) : ProfileUiState()
}