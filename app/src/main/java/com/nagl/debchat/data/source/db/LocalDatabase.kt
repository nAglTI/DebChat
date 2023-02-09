package com.nagl.debchat.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nagl.debchat.data.source.db.dao.ChatMessageDao
import com.nagl.debchat.data.source.db.entity.DBMessage

@Database(
    entities = [
        DBMessage::class,
    ],
    version = 1,
    exportSchema = true
)
abstract class LocalDatabase : RoomDatabase() {
    abstract val chatMessageDao: ChatMessageDao
}