package com.serdar.profile.domain

import com.serdar.profile.data.NetworkResponseState
import com.serdar.profile.data.dto.CryptoPriceResponse
import com.serdar.profile.data.repository.CryptoPriceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCryptoPriceUseCaseImpl @Inject constructor(private val repository: CryptoPriceRepository):GetAllCryptoPriceUseCase{
    override suspend fun invoke(): Flow<NetworkResponseState<CryptoPriceResponse>> =
        repository.getAllCryptoPriceFromRest()

}