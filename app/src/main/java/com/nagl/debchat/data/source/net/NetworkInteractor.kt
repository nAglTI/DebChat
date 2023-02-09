package com.nagl.debchat.data.source.net

import com.nagl.debchat.data.model.net.NetDialog
import com.nagl.debchat.data.model.net.NetMessage
import com.nagl.debchat.data.model.UserToken
import com.nagl.debchat.data.model.net.MessageHistoryRequest
import com.nagl.debchat.data.model.net.UserRequest
import com.nagl.debchat.data.source.net.retrofit.ApiService
import com.nagl.debchat.di.scope.IoDispatcher
import com.nagl.debchat.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkInteractor @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : INetworkInteractor {

    override suspend fun getUserToken(requestData: UserRequest): Result<UserToken> =
        withContext(ioDispatcher) {
            return@withContext try {
                val result = apiService.getUserToken(requestData)
                if (result.isSuccessful) {
                    val userInfo = result.body()
                    Result.Success(userInfo)
                } else {
                    Result.Success(null)
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getUserDialogs(userToken: String): Result<List<NetDialog>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val result = apiService.getDialogs(userToken)
                if (result.isSuccessful) {
                    val dialogs = result.body()
                    Result.Success(dialogs?.dialogsList)
                } else {
                    Result.Success(null)
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getMessages(userToken: String, messageHistoryRequest: MessageHistoryRequest): Result<List<NetMessage>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val result = apiService.getMessageHistory(userToken, messageHistoryRequest)
                if (result.isSuccessful) {
                    val response = result.body()
                    Result.Success(response?.messagesList)
                } else {
                    Result.Success(null)
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}