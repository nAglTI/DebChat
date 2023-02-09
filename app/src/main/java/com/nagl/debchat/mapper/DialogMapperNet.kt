package com.nagl.debchat.mapper

import com.nagl.debchat.data.model.Dialog
import com.nagl.debchat.data.model.net.NetDialog

class DialogMapperNet(private val userMapperNet: UserMapperNet) {
    fun transformToDomain(netDialog: NetDialog): Dialog = Dialog(
        chatId = netDialog.chatId,
        title = netDialog.title,
        users = netDialog.netUsers.map { userMapperNet.transformToDomain(it) }
    )

    fun transformToDto(dialog: Dialog): NetDialog = NetDialog(
        chatId = dialog.chatId,
        title = dialog.title,
        netUsers = dialog.users.map { userMapperNet.transformToDto(it) }
    )
}