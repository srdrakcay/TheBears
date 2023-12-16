package com.serdar.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.serdar.common.distpatcher.DispatcherProvider
import com.serdar.profile.data.NetworkResponseState
import com.serdar.profile.domain.GetAllCryptoPriceUseCase
import com.serdar.socket.socketnetwork.SocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val getAllCryptoPriceUseCase: GetAllCryptoPriceUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val gson: Gson,
    private val socketManager: SocketManager
) : ViewModel() {

    val state = socketManager.events
    private val _currentUser = MutableStateFlow<String>("")
    val currentUser: MutableStateFlow<String> = _currentUser
    private val _signOutState = MutableStateFlow<SignOutState>(SignOutState.Idle)
    val signOutState: StateFlow<SignOutState> = _signOutState

    private val _profileUiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val profileUiState: MutableStateFlow<ProfileUiState> = _profileUiState

    fun userInfo() {
        currentUser.value = firebaseAuth.currentUser?.email.toString()
    }

    fun signOut() {
        viewModelScope.launch(dispatcherProvider.main) {
            _signOutState.value = SignOutState.Loading
            try {
                firebaseAuth.signOut()
                _signOutState.value = SignOutState.Success
            } catch (e: Exception) {
                _signOutState.value = SignOutState.Error(e.message ?: "Sign out failed")
            }
        }

    }

    fun getAllCryptoDataFromRest() {
        viewModelScope.launch(dispatcherProvider.main) {
            getAllCryptoPriceUseCase().onEach {
                when (it) {
                    is NetworkResponseState.Error -> {
                        _profileUiState.value = ProfileUiState.Error(it.exception)
                    }

                    NetworkResponseState.Loading -> {
                        _profileUiState.value = (ProfileUiState.Loading)
                    }

                    is NetworkResponseState.Success -> {
                        _profileUiState.value = (ProfileUiState.Success(it.result!!))
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

}