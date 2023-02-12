package com.nagl.debchat.data.model.net

import com.google.gson.annotations.SerializedName

class SendMessageRequest(
    @SerializedName("chat_id")
    val chatId: Long,
    @SerializedName("aes_string")
    val text: String
)