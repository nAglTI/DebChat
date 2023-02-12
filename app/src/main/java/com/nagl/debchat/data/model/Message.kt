package com.nagl.debchat.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    var id: Long = 0,
    var senderUserId: Long = 0, // TODO: возможно добавить имя юзера
    var date: Long = 0, // timestamp ms
    var text: String = "", // aes str
): Parcelable {

    fun decodeText(): String {
        return "decoded text"
    }

    fun encodeText(): String {
        text = "encoded text"
        return text
    }
}


