package com.serdar.signup.data

import com.google.firebase.Firebase
import com.google.firebase.auth.auth

object ServiceLocator {

    var auth = Firebase.auth

}