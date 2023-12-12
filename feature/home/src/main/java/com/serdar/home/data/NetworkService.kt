package com.serdar.home.data

import com.serdar.home.data.dto.CryptoPriceResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface NetworkService {
    @GET("v2/assets")
    suspend fun getAllCryptoPriceFromRest() : CryptoPriceResponse
}