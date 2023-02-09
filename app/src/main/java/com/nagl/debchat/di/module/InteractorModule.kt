package com.nagl.debchat.di.module

import com.nagl.debchat.data.source.db.DatabaseInteractor
import com.nagl.debchat.data.source.db.IDatabaseInteractor
import com.nagl.debchat.data.source.net.INetworkInteractor
import com.nagl.debchat.data.source.net.NetworkInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class InteractorModule {

    @Binds
    abstract fun bindNetworkInteractor(networkInteractor: NetworkInteractor): INetworkInteractor

    @Binds
    abstract fun bindDatabaseInteractor(databaseInteractor: DatabaseInteractor): IDatabaseInteractor
}