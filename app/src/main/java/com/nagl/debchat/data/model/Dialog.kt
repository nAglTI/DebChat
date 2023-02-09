package com.nagl.debchat.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dialog(
    val chatId: Long,
    val title: String,
    val users: List<User>
): Parcelable