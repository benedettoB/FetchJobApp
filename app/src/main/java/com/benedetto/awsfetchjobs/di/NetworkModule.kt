package com.benedetto.awsfetchjobs.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val END_POINT = "https://fetch-hiring.s3.amazonaws.com/hiring.json"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun providesItemsRequest(): Request {
        return Request.Builder()
            .url(END_POINT)
            .build()
    }
}