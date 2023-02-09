package com.nagl.debchat.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nagl.debchat.data.source.db.entity.DBMessage

@Dao
interface ChatMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessagesByChatId(vararg chatMessage: DBMessage)

    @Query("DELETE FROM chatMessage WHERE chatId = :chatId AND date NOT IN (SELECT date FROM chatMessage WHERE chatId = :chatId ORDER BY date LIMIT 200 )")
    suspend fun deleteLimitByChatId(chatId: Long)

    @Query("SELECT * FROM chatMessage WHERE chatId = :chatId")
    suspend fun getMessagesByChatId(chatId: Long): List<DBMessage>
}