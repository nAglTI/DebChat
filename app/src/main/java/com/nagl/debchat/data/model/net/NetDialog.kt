package com.nagl.debchat.data.model.net

import com.google.gson.annotations.SerializedName

data class NetDialog(
    @SerializedName("chat_id")
    val chatId: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("users")
    val netUsers: List<NetUser>
)