package com.serdar.common

sealed class FirebaseAuthResponse<out T :Any?> {
    data object Loading : FirebaseAuthResponse<Nothing>()
    data class Error(val exception:Exception): FirebaseAuthResponse<Nothing>()
    data class Success<out T:Any>(val result: T?): FirebaseAuthResponse<T>()
}