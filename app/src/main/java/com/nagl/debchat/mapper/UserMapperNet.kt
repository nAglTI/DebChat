package com.nagl.debchat.mapper

import com.nagl.debchat.data.model.User
import com.nagl.debchat.data.model.net.NetUser

class UserMapperNet {
    fun transformToDomain(netUser: NetUser): User = User(
        userId = netUser.userId,
        name = netUser.userName
    )

    fun transformToDto(user: User): NetUser = NetUser(
        userId = user.userId,
        userName = user.name
    )
}