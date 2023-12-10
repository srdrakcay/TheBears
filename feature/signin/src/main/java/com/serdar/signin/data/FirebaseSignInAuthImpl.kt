package com.serdar.signin.data

import com.google.firebase.auth.FirebaseAuth
import com.serdar.common.FirebaseAuthResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseSignInAuthImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    FirebaseSignInAuth {
    override suspend fun signInWithFirebase(
        email: String,
        password: String
    ): Flow<FirebaseAuthResponse<Any>> =
        flow {
            emit(FirebaseAuthResponse.Loading)
            try {
                val response = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                if (response.user != null) {
                    emit(FirebaseAuthResponse.Success(response.user))
                } else {
                    emit(FirebaseAuthResponse.Error(firebaseAuth.pendingAuthResult?.exception!!))
                }
            } catch (e: Exception) {
                emit(FirebaseAuthResponse.Error(firebaseAuth.pendingAuthResult?.exception!!))
            }
        }

}