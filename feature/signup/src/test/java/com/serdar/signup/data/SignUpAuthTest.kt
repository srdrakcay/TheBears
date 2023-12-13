package com.serdar.signup.data

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.serdar.common.response.FirebaseAuthResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


@RunWith(MockitoJUnitRunner::class)
class SignUpAuthTest {

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var signUpAuthImpl: SignUpAuthImpl
    private lateinit var firebaseUser: FirebaseUser

    @Before
    fun setup(){
        firebaseAuth=FirebaseAuth.getInstance()
        signUpAuthImpl= SignUpAuthImpl(firebaseAuth)
    }
    @Test
    fun createTest() {
        val email = "srdrakcay23@gmail.com"
        val password = "123456"
        val result=firebaseAuth.createUserWithEmailAndPassword(email,password).result.user
        runBlocking {
            signUpAuthImpl.createNewUser(email, password).collect(){
                assertThat(result).isEqualTo(result)
            }
        }
    }
}



