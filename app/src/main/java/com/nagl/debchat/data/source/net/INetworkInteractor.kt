package com.nagl.debchat.data.source.net

import com.nagl.debchat.data.model.UserToken
import com.nagl.debchat.data.model.net.*
import com.nagl.debchat.utils.Result

interface INetworkInteractor {

    suspend fun getUserToken(requestData: UserRequest): Result<UserToken>

    suspend fun getUserDialogs(userToken: String): Result<List<NetDialog>>

    suspend fun getMessages(userToken: String, messageHistoryRequest: MessageHistoryRequest): Result<List<NetMessage>>

    suspend fun sendMessage(userToken: String, sendMessageRequest: SendMessageRequest): Result<NetMessage>

    //suspend fun getUserScheduleByGroup(group: String): Result<UserToken>
}