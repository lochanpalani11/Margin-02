package com.margin.app.di

import com.margin.app.data.repository.HaulRepositoryImpl
import com.margin.app.data.repository.ItemRepositoryImpl
import com.margin.app.domain.repository.HaulRepository
import com.margin.app.domain.repository.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindItemRepository(impl: ItemRepositoryImpl): ItemRepository

    @Binds
    @Singleton
    abstract fun bindHaulRepository(impl: HaulRepositoryImpl): HaulRepository
}
