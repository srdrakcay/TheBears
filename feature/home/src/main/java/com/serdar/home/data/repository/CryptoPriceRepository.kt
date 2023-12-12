package com.serdar.home.data.repository

import com.serdar.home.data.NetworkResponseState
import com.serdar.home.data.dto.CryptoPriceResponse
import kotlinx.coroutines.flow.Flow

interface CryptoPriceRepository {
    suspend fun getAllCryptoPriceFromRest(): Flow<NetworkResponseState<CryptoPriceResponse>>

}