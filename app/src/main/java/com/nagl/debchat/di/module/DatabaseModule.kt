package com.nagl.debchat.di.module

import android.content.Context
import androidx.room.Room
import com.nagl.debchat.data.source.db.LocalDatabase
import com.nagl.debchat.data.source.db.dao.ChatMessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java, "Memes.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideChatMessageDao(database: LocalDatabase): ChatMessageDao {
        return database.chatMessageDao
    }
}