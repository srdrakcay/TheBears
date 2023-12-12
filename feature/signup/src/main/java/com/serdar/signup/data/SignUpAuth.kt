package com.serdar.signup.data

import com.serdar.common.response.FirebaseAuthResponse
import kotlinx.coroutines.flow.Flow

interface SignUpAuth {
    suspend fun createNewUser(email: String, password: String): Flow<FirebaseAuthResponse<Any>>
}