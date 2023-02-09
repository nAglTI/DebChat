package com.nagl.debchat.data.source.net.retrofit

import com.nagl.debchat.data.model.net.NetUser
import com.nagl.debchat.data.model.net.UserRequest
import com.nagl.debchat.utils.Urls
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST(Urls.AUTH_URL)
    suspend fun getUserInfo(@Body userRequest: UserRequest): Response<NetUser>
}