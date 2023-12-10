package com.serdar.signup.domain

import com.serdar.common.FirebaseAuthResponse
import com.serdar.signup.data.SignUpAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpWithEmailUseCase @Inject constructor(private val signUpAuth: SignUpAuth){
    suspend operator fun invoke(email:String,password:String): Flow<FirebaseAuthResponse<Any>> {
        return signUpAuth.createNewUser(email, password)
    }
}