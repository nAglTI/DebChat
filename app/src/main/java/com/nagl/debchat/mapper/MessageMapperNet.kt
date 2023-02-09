package com.nagl.debchat.mapper

import com.nagl.debchat.data.model.Message
import com.nagl.debchat.data.model.net.NetMessage

class MessageMapperNet {
    fun transformToDomain(netMessage: NetMessage): Message = Message(
        id = netMessage.id,
        senderUserId = netMessage.senderUserId,
        date = netMessage.date,
        text = netMessage.text
    )

    fun transformToDto(message: Message): NetMessage = NetMessage(
        id = message.id,
        senderUserId = message.senderUserId,
        date = message.date,
        text = message.text
    )
}