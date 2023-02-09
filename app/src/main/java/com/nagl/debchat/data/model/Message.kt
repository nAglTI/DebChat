package com.nagl.debchat.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    val id: Long,
    val senderUserId: Long, // TODO: возможно добавить имя юзера
    val date: Long, // timestamp ms
    var text: String, // aes str
): Parcelable {

    fun Message.decodeText(): String {
        return "decoded text"
    }

    fun Message.encodeText() {
        text = "encoded text"
    }
}


