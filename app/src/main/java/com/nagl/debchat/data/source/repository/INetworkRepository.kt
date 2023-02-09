package com.nagl.debchat.data.source.repository

import com.nagl.debchat.data.model.UserToken
import com.nagl.debchat.data.model.net.UserRequest
import com.nagl.debchat.utils.Result

interface INetworkRepository {

    suspend fun getUserInfo(requestData: UserRequest): Result<UserToken>
}