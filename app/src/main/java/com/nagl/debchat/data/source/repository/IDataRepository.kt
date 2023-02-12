package com.nagl.debchat.data.source.repository

import com.nagl.debchat.data.model.Dialog
import com.nagl.debchat.data.model.Message
import com.nagl.debchat.data.model.net.NetDialog
import com.nagl.debchat.data.model.net.NetMessage
import com.nagl.debchat.data.model.UserToken
import com.nagl.debchat.data.model.net.UserRequest
import com.nagl.debchat.utils.Result

interface IDataRepository {

    suspend fun getUserToken(requestData: UserRequest): Result<UserToken>

    suspend fun getUserDialogs(userToken: String): Result<List<Dialog>>

    suspend fun getCacheMessages(chatId: Long): Result<List<Message>>

    suspend fun getNetMessages(userToken: String, chatId: Long, startMessageId: Long): Result<List<Message>>

    suspend fun sendMessage(userToken: String, message: Message, chatId: Long): Result<Message>

    suspend fun insertMessagesToCache(chatId: Long, list: List<Message>)
//
//    suspend fun getUserScheduleByGroup(group: String, refresh: Boolean): Result<Schedule>
//
//    suspend fun saveSchedule(schedule: Schedule)
//
//    suspend fun deleteSchedule()
}