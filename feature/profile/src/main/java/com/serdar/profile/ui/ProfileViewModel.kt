package com.serdar.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.serdar.socket.socketnetwork.SocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth,
                                           private val socketManager: SocketManager) : ViewModel() {

    val state = socketManager.events
    private val _currentUser = MutableStateFlow<String>("")
    val currentUser: MutableStateFlow<String> = _currentUser
    private val _signOutState = MutableStateFlow<SignOutState>(SignOutState.Idle)
    val signOutState: StateFlow<SignOutState> = _signOutState
    fun userInfo() {
        currentUser.value = firebaseAuth.currentUser?.email.toString()
    }

    fun signOut() {
        viewModelScope.launch {
            _signOutState.value = SignOutState.Loading
            try {
                firebaseAuth.signOut()
                _signOutState.value = SignOutState.Success
            } catch (e: Exception) {
                _signOutState.value = SignOutState.Error(e.message ?: "Sign out failed")
            }
        }

    }
}