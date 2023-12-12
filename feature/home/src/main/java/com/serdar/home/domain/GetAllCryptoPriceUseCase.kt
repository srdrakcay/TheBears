package com.serdar.home.domain

import com.serdar.home.data.NetworkResponseState
import com.serdar.home.data.dto.CryptoPriceResponse
import com.serdar.home.data.repository.CryptoPriceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCryptoPriceUseCase @Inject constructor(private val repository: CryptoPriceRepository){
    suspend operator fun invoke(): Flow<NetworkResponseState<CryptoPriceResponse>> =
         repository.getAllCryptoPriceFromRest()

}