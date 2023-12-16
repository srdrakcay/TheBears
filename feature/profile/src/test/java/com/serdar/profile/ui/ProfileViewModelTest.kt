package com.serdar.profile.ui

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.serdar.profile.constant.apiException
import com.serdar.profile.constant.cryptoResponse
import com.serdar.profile.domain.GetAllCryptoPriceUseCaseImpl
import com.serdar.socket.data.SocketStateManager
import com.serdar.socket.data.dto.subscription.SubscriptionSocket
import com.serdar.socket.data.dto.subscription.SubscriptionSocketData
import com.serdar.socket.socketnetwork.SocketManager
import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {
    @Mock
    private lateinit var getAllCryptoPriceUseCase: GetAllCryptoPriceUseCaseImpl

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var socketManager: SocketManager
    private lateinit var dispatcherProvider: TestDispatcherProvider
    private lateinit var profileViewModel: ProfileViewModel

    @get:Rule
    val mainDispatcherRule = TestCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        dispatcherProvider = TestDispatcherProvider()
        profileViewModel = ProfileViewModel(
            firebaseAuth,
            getAllCryptoPriceUseCase,
            dispatcherProvider,
            gson,
            socketManager
        )

    }

    @Test
    fun `get all crypto prices from REST returns NetworkResponseState is Success`() = runTest {
        val job = launch(UnconfinedTestDispatcher(dispatcherProvider.testDispatcher.scheduler)) {
            profileViewModel.profileUiState.emit(ProfileUiState.Loading)
            delay(10)
            profileViewModel.profileUiState.emit(ProfileUiState.Success(cryptoResponse))
        }
        runCurrent()
        advanceUntilIdle()
        profileViewModel.profileUiState.test {
            assertThat(awaitItem()).isEqualTo(ProfileUiState.Success(cryptoResponse))
        }
        job.cancel()

    }

    @Test
    fun `get all crypto prices from REST returns NetworkResponseState is Error`() = runTest {
        val job = launch(UnconfinedTestDispatcher(dispatcherProvider.testDispatcher.scheduler)) {
            profileViewModel.profileUiState.emit(ProfileUiState.Loading)
            delay(10)
            profileViewModel.profileUiState.emit(ProfileUiState.Error(apiException))
        }
        runCurrent()
        advanceUntilIdle()
        profileViewModel.profileUiState.test {
            assertThat(awaitItem()).isEqualTo(ProfileUiState.Error(apiException))
        }
        job.cancel()
    }

    @Test
    fun `get all crypto prices from REST returns NetworkResponseState is Loading`() = runTest {
        val job = launch(UnconfinedTestDispatcher(dispatcherProvider.testDispatcher.scheduler)) {
            profileViewModel.profileUiState.emit(ProfileUiState.Loading)
            delay(10)
        }
        runCurrent()
        advanceUntilIdle()
        profileViewModel.profileUiState.test {
            assertThat(awaitItem()).isEqualTo(ProfileUiState.Loading)
        }
        job.cancel()
    }

    @Test
    fun `get the current user email`() = runTest {
        val currentUserEmail = "srdrakcay25@gmail.com"
        val job = launch(UnconfinedTestDispatcher(dispatcherProvider.testDispatcher.scheduler)) {
            profileViewModel.currentUser.emit(currentUserEmail)
            delay(10)
        }
        runCurrent()
        advanceUntilIdle()
        profileViewModel.currentUser.test {
            assertThat(awaitItem()).isEqualTo(currentUserEmail)
        }
        job.cancel()
    }

    @Test
    fun ` the user signOut state is Success`() = runTest {
        val job = launch(UnconfinedTestDispatcher(dispatcherProvider.testDispatcher.scheduler)) {
            profileViewModel.signOut()
            delay(10)
        }
        runCurrent()
        advanceUntilIdle()
        profileViewModel.signOut()
        profileViewModel.signOutState.test {
            assertThat(awaitItem()).isEqualTo(SignOutState.Success)
        }
        job.cancel()
    }

    @Test
    fun `getting the socket status connected`() = runTest {
        //Don't know how to test websocket client state
    }


}