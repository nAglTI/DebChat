package com.nagl.debchat.data.source.db

import com.nagl.debchat.data.model.net.NetMessage
import com.nagl.debchat.data.source.db.entity.DBMessage

interface IDatabaseInteractor {

    suspend fun getCacheMessagesByChatId(chatId: Long): List<DBMessage>

    suspend fun insertMessagesByChatId(chatId: Long, netMessages: List<DBMessage>)
}