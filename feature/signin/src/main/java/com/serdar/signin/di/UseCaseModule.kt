package com.serdar.signin.di

import com.serdar.signin.domain.FirebaseCreateUserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {


    @Binds
    @ViewModelScoped
    abstract fun bindFirebaseCreateUserUseCase(firebaseCreateUserUseCase: FirebaseCreateUserUseCase): FirebaseCreateUserUseCase
}