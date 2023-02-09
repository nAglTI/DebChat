package com.nagl.debchat.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userId: Long,
    val name: String
): Parcelable
