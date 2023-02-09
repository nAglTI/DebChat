package com.nagl.debchat.mapper

import com.nagl.debchat.data.model.Message
import com.nagl.debchat.data.model.net.NetMessage
import com.nagl.debchat.data.source.db.entity.DBMessage

class MessageMapperDB {
    fun transformToDomain(dbMessage: DBMessage): Message = Message(
        id = dbMessage.messageId,
        senderUserId = dbMessage.senderUserId,
        date = dbMessage.date,
        text = dbMessage.text
    )

    fun transformToDto(message: Message, chatId: Long): DBMessage = DBMessage(
        chatId = chatId,
        messageId = message.id,
        senderUserId = message.senderUserId,
        date = message.date,
        text = message.text
    )
}