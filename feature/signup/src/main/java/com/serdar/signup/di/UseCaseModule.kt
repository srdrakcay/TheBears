package com.serdar.signup.di

import com.serdar.signup.domain.SignUpWithEmailUseCase
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
    abstract fun bindSignUpUserUseCase(signUpWithEmailUseCase: SignUpWithEmailUseCase): SignUpWithEmailUseCase
}