package com.serdar.profile.data.repository

import com.serdar.profile.data.NetworkResponseState
import com.serdar.profile.data.dto.CryptoPriceResponse
import kotlinx.coroutines.flow.Flow

interface CryptoPriceRepository {
    suspend fun getAllCryptoPriceFromRest(): Flow<NetworkResponseState<CryptoPriceResponse>>

}