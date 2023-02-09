package com.nagl.debchat.data.model.net

import com.google.gson.annotations.SerializedName

data class NetUser(
//    val accountExpirationDate: String = "",
//    val accountLockoutTime: String = "",
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("name")
    val userName: String,
)
