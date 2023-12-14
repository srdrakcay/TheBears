package com.serdar.profile.data.repository

import com.serdar.profile.constant.apiException
import com.serdar.profile.constant.cryptoResponse
import com.serdar.profile.data.NetworkResponseState
import com.serdar.profile.data.dto.CryptoPriceResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCryptoPriceRepositoryImpl : CryptoPriceRepository {

    private var showError = false
    private var showLoading = false

    fun updateShowError(showError: Boolean) {
        this.showError = showError
    }

    fun updateShowLoading(showLoading: Boolean) {
        this.showLoading = showLoading
    }

    override suspend fun getAllCryptoPriceFromRest(): Flow<NetworkResponseState<CryptoPriceResponse>> {
        return flow {
            if (showLoading) {
                emit(NetworkResponseState.Loading)
            } else {
                if (showError) {
                    emit(NetworkResponseState.Error(apiException))
                } else {
                    emit(NetworkResponseState.Success(cryptoResponse))
                }
            }
        }
    }
}