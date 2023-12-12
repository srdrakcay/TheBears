package com.serdar.home.di

import com.serdar.home.data.repository.CryptoPriceRepository
import com.serdar.home.data.repository.CryptoPriceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {


    @Binds
    @ViewModelScoped
    abstract fun bindRepository(cryptoPriceRepositoryImpl: CryptoPriceRepositoryImpl
    ): CryptoPriceRepository
}