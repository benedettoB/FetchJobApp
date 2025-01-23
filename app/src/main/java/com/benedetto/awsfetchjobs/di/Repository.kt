package com.benedetto.awsfetchjobs.di


import com.benedetto.data.respository.ItemsRepositoryImpl
import com.benedetto.domain.repositories.ItemRepository
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
    abstract fun bindItemRepository(
        itemsRepositoryImpl: ItemsRepositoryImpl
    ): ItemRepository
}