package com.serdar.common.di

import com.serdar.common.distpatcher.DefaultDispatcherProvider
import com.serdar.common.distpatcher.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {


    @Binds
    @Singleton
    abstract fun bindDispatcher(
        defaultDispatcherProvider: DefaultDispatcherProvider
    ): DispatcherProvider
}