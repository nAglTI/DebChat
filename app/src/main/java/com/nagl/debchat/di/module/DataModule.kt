package com.nagl.debchat.di.module

import android.content.Context
import com.nagl.debchat.data.source.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDataStoreRepository(context: Context) = DataStoreRepository(context)

}
