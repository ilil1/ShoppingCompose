package com.example.shoppingcompose.di

import com.example.data.repository.TempRepositoryImpl
import com.example.domain.repository.TempRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindTempRepository(tempRepository: TempRepositoryImpl) : TempRepository
}