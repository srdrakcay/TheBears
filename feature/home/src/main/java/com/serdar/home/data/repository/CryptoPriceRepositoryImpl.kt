package com.serdar.home.data.repository

import android.util.Log
import com.serdar.home.data.NetworkResponseState
import com.serdar.home.data.NetworkService
import com.serdar.home.data.dto.CryptoPriceResponse
import com.serdar.home.di.IoDispatcher
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