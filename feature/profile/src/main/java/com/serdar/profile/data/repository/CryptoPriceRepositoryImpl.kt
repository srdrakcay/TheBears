package com.serdar.profile.data.repository

import com.serdar.profile.data.NetworkResponseState
import com.serdar.profile.data.NetworkService
import com.serdar.profile.data.dto.CryptoPriceResponse
import com.serdar.profile.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CryptoPriceRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CryptoPriceRepository {

    override suspend fun getAllCryptoPriceFromRest(): Flow<NetworkResponseState<CryptoPriceResponse>> =
        flow {
            try {
                emit(NetworkResponseState.Loading)
                val response = networkService.getAllCryptoPriceFromRest()
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }.flowOn(ioDispatcher)

}