package com.nagl.debchat.data.source.repository

import com.nagl.debchat.data.model.UserToken
import com.nagl.debchat.data.model.net.UserRequest
import com.nagl.debchat.data.source.net.INetworkInteractor
import com.nagl.debchat.di.scope.IoDispatcher
import com.nagl.debchat.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkInteractor: INetworkInteractor,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): INetworkRepository {

    override suspend fun getUserInfo(requestData: UserRequest): Result<UserToken> =
        withContext(ioDispatcher) {
            when (val response = networkInteractor.getUserToken(requestData)) {
                is Result.Success -> {
                    response
                }
                is Result.Error -> response
                else -> Result.Loading
            }
        }
}