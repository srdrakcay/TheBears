package com.serdar.profile.domain

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.serdar.profile.constant.apiException
import com.serdar.profile.constant.cryptoResponse
import com.serdar.profile.data.NetworkResponseState
import com.serdar.profile.data.repository.FakeCryptoPriceRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class GetAllCryptoPriceUseCaseTest {
    @Mock
    private lateinit var fakeGetAllCryptoPriceUseCaseImpl: FakeGetAllCryptoPriceUseCaseImpl
    private lateinit var fakeCryptoPriceRepositoryImpl: FakeCryptoPriceRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fakeCryptoPriceRepositoryImpl= FakeCryptoPriceRepositoryImpl()
        fakeGetAllCryptoPriceUseCaseImpl = FakeGetAllCryptoPriceUseCaseImpl(fakeCryptoPriceRepositoryImpl)
    }

    @Test
    fun `get all crypto prices from REST returns NetworkResponseState is Success`() {
        runBlocking {
            fakeGetAllCryptoPriceUseCaseImpl.invoke().test {
                Truth.assertThat(awaitItem()).isEqualTo(NetworkResponseState.Success(cryptoResponse))
                awaitComplete()
            }

        }
    }

    @Test
    fun `get all crypto prices from REST returns NetworkResponseState is Error`() {
        runBlocking {
            fakeGetAllCryptoPriceUseCaseImpl.updateShowError(true)
            fakeGetAllCryptoPriceUseCaseImpl.invoke().test {
                Truth.assertThat(awaitItem()).isEqualTo(NetworkResponseState.Error(apiException))
                awaitComplete()
            }

        }
    }
    @Test
    fun `get all crypto prices from REST returns NetworkResponseState is Loading`() {
        runBlocking {
            fakeGetAllCryptoPriceUseCaseImpl.updateShowLoading(true)
            fakeGetAllCryptoPriceUseCaseImpl.invoke().test {
                Truth.assertThat(awaitItem()).isEqualTo(NetworkResponseState.Loading)
                awaitComplete()
            }
        }
    }

    @Test
    fun `get all crypto prices from REST returns isNotNull`() {
        runBlocking {
            fakeGetAllCryptoPriceUseCaseImpl.invoke().test {
                Truth.assertThat(awaitItem()).isNotNull()
                awaitComplete()
            }
        }
    }

    @Test
    fun `get all crypto prices from REST returns isAnyOf Success, Error, or Loading `() {
        runBlocking {
            fakeGetAllCryptoPriceUseCaseImpl.updateShowError(true)
            fakeGetAllCryptoPriceUseCaseImpl.updateShowLoading(true)
            fakeGetAllCryptoPriceUseCaseImpl.invoke().test {
                val success = NetworkResponseState.Success(cryptoResponse)
                val error = NetworkResponseState.Error(apiException)
                val loading = NetworkResponseState.Loading
                Truth.assertThat(awaitItem()).isAnyOf(success, error, loading)
                awaitComplete()
            }
        }
    }
}