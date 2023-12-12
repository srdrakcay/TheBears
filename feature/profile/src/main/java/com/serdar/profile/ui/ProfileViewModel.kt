package com.serdar.profile.ui

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) : ViewModel() {

    private val _currentUser = MutableStateFlow<String>("")
    val currentUser: MutableStateFlow<String> = _currentUser
    fun userInfo() {
        currentUser.value = firebaseAuth.currentUser?.email.toString()
    }

    fun signOut() {
        firebaseAuth.signOut()

    }
}