package com.nagl.debchat.data.source.repository

import com.nagl.debchat.data.model.Dialog
import com.nagl.debchat.data.model.Message
import com.nagl.debchat.data.model.net.NetDialog
import com.nagl.debchat.data.model.net.NetMessage
import com.nagl.debchat.data.model.UserToken
import com.nagl.debchat.data.model.net.MessageHistoryRequest
import com.nagl.debchat.data.model.net.UserRequest
import com.nagl.debchat.data.source.db.IDatabaseInteractor
import com.nagl.debchat.data.source.net.INetworkInteractor
import com.nagl.debchat.di.scope.IoDispatcher
import com.nagl.debchat.mapper.DialogMapperNet
import com.nagl.debchat.mapper.MessageMapperDB
import com.nagl.debchat.mapper.MessageMapperNet
import com.nagl.debchat.mapper.UserMapperNet
import com.nagl.debchat.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val networkInteractor: INetworkInteractor,
    private val databaseInteractor: IDatabaseInteractor,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IDataRepository {

    override suspend fun getUserToken(requestData: UserRequest): Result<UserToken> =
        withContext(ioDispatcher) {
            when (val response = networkInteractor.getUserToken(requestData)) {
                is Result.Success, is Result.Error -> response
                else -> Result.Loading
            }
        }

    override suspend fun getUserDialogs(userToken: String): Result<List<Dialog>> =
        withContext(ioDispatcher) {
            val mapper = DialogMapperNet(UserMapperNet())
            when (val response = networkInteractor.getUserDialogs(userToken)) {
                is Result.Success -> if (response.data != null) Result.Success(response.data.map { mapper.transformToDomain(it) }) else Result.Success(null)
                is Result.Error ->Result.Error(response.exception)
                else -> Result.Loading
            }
        }

    override suspend fun getCacheMessages(chatId: Long): Result<List<Message>> =
        withContext(ioDispatcher) {
            val result = databaseInteractor.getCacheMessagesByChatId(chatId)
            val mapper = MessageMapperDB()
            if (result != null && result.isNotEmpty()) {
                Result.Success(
                    result.map { mapper.transformToDomain(it) }
                )
            } else Result.Success(null)
        }

    override suspend fun getNetMessages(
        userToken: String,
        chatId: Long,
        startMessageId: Long
    ): Result<List<Message>> =
        withContext(ioDispatcher) {
            val mapper = MessageMapperNet()
            when (val response = networkInteractor.getMessages(userToken, MessageHistoryRequest(chatId, 200, startMessageId))) {
                is Result.Success -> if (response.data != null) Result.Success(response.data.map { mapper.transformToDomain(it) }) else Result.Success(null)
                is Result.Error -> Result.Error(response.exception)
                else -> Result.Loading
            }
        }
}