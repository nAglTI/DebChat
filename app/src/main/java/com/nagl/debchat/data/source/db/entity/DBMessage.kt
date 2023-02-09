package com.nagl.debchat.data.source.db.entity

import androidx.room.Entity

@Entity(tableName = "chatMessage", primaryKeys = ["chatId", "messageId"])
data class DBMessage(
    var chatId: Long,
    var messageId: Long,
    var senderUserId: Long,
    var date: Long,
    var text: String,
)