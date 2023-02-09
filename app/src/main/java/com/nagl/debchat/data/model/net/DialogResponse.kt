package com.nagl.debchat.data.model.net

import com.google.gson.annotations.SerializedName

class DialogResponse(
    @SerializedName("dialogs_list")
    val dialogsList: List<NetDialog>
)