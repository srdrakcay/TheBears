package com.serdar.signin.data

import com.serdar.common.FirebaseAuthResponse
import kotlinx.coroutines.flow.Flow

interface FirebaseSignInAuth {
    suspend fun signInWithFirebase(email: String, password: String): Flow<FirebaseAuthResponse<Any>>

}