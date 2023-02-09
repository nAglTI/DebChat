package com.nagl.debchat.data.model.net

import com.google.gson.annotations.SerializedName

data class MessageHistoryRequest(
    @SerializedName("chat_id")
    val chatId: Long,
    @SerializedName("messages_count")
    val messagesCount: Int,
    @SerializedName("start_message")
    val startMessage: Long
)
