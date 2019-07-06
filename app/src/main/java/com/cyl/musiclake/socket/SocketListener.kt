package com.cyl.musiclake.socket

import com.cyl.musiclake.bean.MessageInfoBean
import com.cyl.musiclake.bean.UserInfoBean


interface SocketListener {

    fun onLeaveEvent(user: UserInfoBean)


    fun onJoinEvent(user: UserInfoBean)


    fun onOnlineUsers(users: MutableList<UserInfoBean>)


    fun onMessage(msgInfo: MessageInfoBean)


    fun onError(msg: String)
}