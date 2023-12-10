package com.serdar.signin.domain

import com.serdar.common.FirebaseAuthResponse
import com.serdar.signin.data.FirebaseSignInAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirebaseCreateUserUseCase @Inject constructor(private val firebaseSignInAuth: FirebaseSignInAuth){
    suspend operator fun invoke(email:String,password:String):Flow<FirebaseAuthResponse<Any>> {
        return firebaseSignInAuth.signInWithFirebase(email, password)
    }
}