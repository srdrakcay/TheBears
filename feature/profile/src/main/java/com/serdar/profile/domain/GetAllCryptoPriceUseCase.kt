package com.serdar.profile.domain

import com.serdar.profile.data.NetworkResponseState
import com.serdar.profile.data.dto.CryptoPriceResponse
import kotlinx.coroutines.flow.Flow

interface GetAllCryptoPriceUseCase {
    suspend operator fun invoke(): Flow<NetworkResponseState<CryptoPriceResponse>>
}