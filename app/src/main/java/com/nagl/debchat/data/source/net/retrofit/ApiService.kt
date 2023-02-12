package com.nagl.debchat.data.source.net.retrofit

import com.nagl.debchat.data.model.UserToken
import com.nagl.debchat.data.model.net.*
import com.nagl.debchat.utils.Urls
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST(Urls.AUTH_URL)
    suspend fun getUserToken(@Body userRequest: UserRequest): Response<UserToken>

    @POST("{userToken}/" + Urls.DIALOGS_URL)
    suspend fun getDialogs(@Path("userToken") token: String): Response<DialogResponse>

    @POST("{userToken}/" + Urls.MESSAGES_URL)
    suspend fun getMessageHistory(@Path("userToken") token: String, @Body messageHistoryRequest: MessageHistoryRequest): Response<MessageHistoryResponse>

    @POST("{userToken}/" + Urls.SEND_MESSAGE_URL)
    suspend fun sendMessage(@Path("userToken") token: String, @Body sendMessageRequest: SendMessageRequest): Response<NetMessage>
}