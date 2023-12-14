package com.serdar.profile.di

import com.serdar.profile.domain.GetAllCryptoPriceUseCase
import com.serdar.profile.domain.GetAllCryptoPriceUseCaseImpl
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
    abstract fun bindUseCase(
        getAllCryptoPriceUseCaseImpl: GetAllCryptoPriceUseCaseImpl
    ): GetAllCryptoPriceUseCase
}