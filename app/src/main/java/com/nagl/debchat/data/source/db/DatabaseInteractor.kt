package com.nagl.debchat.data.source.db

import com.nagl.debchat.data.source.db.dao.ChatMessageDao
import com.nagl.debchat.data.source.db.entity.DBMessage
import com.nagl.debchat.di.scope.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DatabaseInteractor @Inject constructor(
    private val chatMessageDao: ChatMessageDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : IDatabaseInteractor {

    override suspend fun getCacheMessagesByChatId(chatId: Long): List<DBMessage> =
        withContext(ioDispatcher) {
            return@withContext chatMessageDao.getMessagesByChatId(chatId)
        }

    override suspend fun insertMessagesByChatId(chatId: Long, messages: List<DBMessage>) {
        withContext(ioDispatcher) {
            messages.forEach {
                chatMessageDao.insertMessagesByChatId(it)
            }
            chatMessageDao.deleteLimitByChatId(chatId)
        }
    }

}