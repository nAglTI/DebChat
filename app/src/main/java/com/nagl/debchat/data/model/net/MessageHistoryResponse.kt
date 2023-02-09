package com.nagl.debchat.data.model.net

import com.google.gson.annotations.SerializedName

class MessageHistoryResponse(
    @SerializedName("messages_list")
    val messagesList: List<NetMessage>
)