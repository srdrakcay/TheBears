package com.serdar.profile.data

import com.serdar.profile.data.dto.CryptoPriceResponse
import retrofit2.http.GET

interface NetworkService {
    @GET("v2/assets")
    suspend fun getAllCryptoPriceFromRest() : CryptoPriceResponse
}