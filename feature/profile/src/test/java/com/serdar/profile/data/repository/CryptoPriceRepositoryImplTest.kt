package com.serdar.profile.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.serdar.profile.constant.apiException
import com.serdar.profile.constant.cryptoResponse
import com.serdar.profile.data.NetworkResponseState
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CryptoPriceRepositoryImplTest {

    @Mock
    private lateinit var fakeCryptoPriceRepositoryImpl: FakeCryptoPriceRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fakeCryptoPriceRepositoryImpl = FakeCryptoPriceRepositoryImpl()
    }

    @Test
    fun `get all crypto prices from REST returns NetworkResponseState is Succes`() {
        runBlocking {
            fakeCryptoPriceRepositoryImpl.getAllCryptoPriceFromRest().test {
                assertThat(awaitItem()).isEqualTo(NetworkResponseState.Success(cryptoResponse))
                awaitComplete()
            }

        }
    }

    @Test
    fun `get all crypto prices from REST returns NetworkResponseState is Error`() {
        runBlocking {
            fakeCryptoPriceRepositoryImpl.updateShowError(true)
            fakeCryptoPriceRepositoryImpl.getAllCryptoPriceFromRest().test {
                assertThat(awaitItem()).isEqualTo(NetworkResponseState.Error(apiException))
                awaitComplete()
            }

        }
    }

    @Test
    fun `get all crypto prices from REST returns NetworkResponseState is Loading`() {
        runBlocking {
            fakeCryptoPriceRepositoryImpl.updateShowLoading(true)
            fakeCryptoPriceRepositoryImpl.getAllCryptoPriceFromRest().test {
                assertThat(awaitItem()).isEqualTo(NetworkResponseState.Loading)
                awaitComplete()
            }
        }
    }

    @Test
    fun `get all crypto prices from REST returns isNotNull`() {
        runBlocking {
            fakeCryptoPriceRepositoryImpl.getAllCryptoPriceFromRest().test {
                assertThat(awaitItem()).isNotNull()
                awaitComplete()
            }
        }
    }

    @Test
    fun `get all crypto prices from REST returns isAnyOf Success, Error, or Loading `() {
        runBlocking {
            fakeCryptoPriceRepositoryImpl.updateShowError(true)
            fakeCryptoPriceRepositoryImpl.updateShowLoading(true)
            fakeCryptoPriceRepositoryImpl.getAllCryptoPriceFromRest().test {
                val success = NetworkResponseState.Success(cryptoResponse)
                val error = NetworkResponseState.Error(apiException)
                val loading = NetworkResponseState.Loading
                assertThat(awaitItem()).isAnyOf(success, error, loading)
                awaitComplete()
            }
        }
    }
}