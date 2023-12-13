package com.serdar.signup.data

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.serdar.common.response.FirebaseAuthResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignUpAuthImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    SignUpAuth {
    override suspend fun createNewUser(
        email: String,
        password: String
    ): Flow<FirebaseAuthResponse<FirebaseUser>> =
        flow {
            emit(FirebaseAuthResponse.Loading)
            try {
                val response = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                if (response.user != null) {
                    emit(FirebaseAuthResponse.Success(response.user))
                } else {
                    emit(FirebaseAuthResponse.Error(java.lang.Exception()))
                }
            } catch (e: Exception) {
                emit(FirebaseAuthResponse.Error(java.lang.Exception()))
            }
        }
}