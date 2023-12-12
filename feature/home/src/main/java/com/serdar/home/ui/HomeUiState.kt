package com.serdar.home.ui

import com.serdar.home.data.dto.CryptoPriceResponse
import java.lang.Exception

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Success(val data: CryptoPriceResponse) : HomeUiState()
    data class Error( val message: Exception) : HomeUiState()
}