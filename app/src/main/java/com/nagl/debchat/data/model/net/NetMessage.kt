package com.nagl.debchat.data.model.net

import com.google.gson.annotations.SerializedName

data class NetMessage(
    val id: Long,
    @SerializedName("from_id")
    val senderUserId: Long, // TODO: возможно добавить имя юзера
    val date: Long, // timestamp ms
    val text: String, // aes str
)