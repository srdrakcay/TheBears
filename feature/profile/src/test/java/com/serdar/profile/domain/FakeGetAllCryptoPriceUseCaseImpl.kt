package com.serdar.profile.domain

import com.serdar.profile.constant.apiException
import com.serdar.profile.constant.cryptoResponse
import com.serdar.profile.data.NetworkResponseState
import com.serdar.profile.data.dto.CryptoPriceResponse
import com.serdar.profile.data.repository.FakeCryptoPriceRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetAllCryptoPriceUseCaseImpl(private val fakeCryptoPriceRepositoryImpl: FakeCryptoPriceRepositoryImpl) :
    GetAllCryptoPriceUseCase {

    private var showError = false
    private var showLoading = false

    fun updateShowError(showError: Boolean) {
        this.showError = showError
    }

    fun updateShowLoading(showLoading: Boolean) {
        this.showLoading = showLoading
    }

    override suspend fun invoke(): Flow<NetworkResponseState<CryptoPriceResponse>> {
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