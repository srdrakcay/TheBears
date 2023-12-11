package com.serdar.signup.di

import com.serdar.signup.data.SignUpAuth
import com.serdar.signup.data.SignUpAuthImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthModule {

    @Binds
    @ViewModelScoped
    abstract fun bindSignUpAuth(signUpAuth: SignUpAuthImpl): SignUpAuth
}