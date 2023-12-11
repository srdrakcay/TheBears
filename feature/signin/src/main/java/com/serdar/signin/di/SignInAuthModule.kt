package com.serdar.signin.di

import com.serdar.signin.data.FirebaseSignInAuth
import com.serdar.signin.data.FirebaseSignInAuthImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class SignInAuthModule {


    @Binds
    @ViewModelScoped
    abstract fun bindFirebaseSignInAuth(firebaseSignInAuthImpl: FirebaseSignInAuthImpl): FirebaseSignInAuth
}