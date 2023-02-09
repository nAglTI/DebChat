package com.nagl.debchat.data.source.net

import com.nagl.debchat.data.model.net.NetDialog
import com.nagl.debchat.data.model.net.NetMessage
import com.nagl.debchat.data.model.UserToken
import com.nagl.debchat.data.model.net.MessageHistoryRequest
import com.nagl.debchat.data.model.net.UserRequest
import com.nagl.debchat.utils.Result

interface INetworkInteractor {

    suspend fun getUserToken(requestData: UserRequest): Result<UserToken>

    suspend fun getUserDialogs(userToken: String): Result<List<NetDialog>>

    suspend fun getMessages(userToken: String, messageHistoryRequest: MessageHistoryRequest): Result<List<NetMessage>>

    //suspend fun getUserScheduleByGroup(group: String): Result<UserToken>
}