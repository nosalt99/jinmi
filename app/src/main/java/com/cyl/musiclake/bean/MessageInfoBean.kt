package com.cyl.musiclake.bean

import org.litepal.crud.LitePalSupport


class MessageInfoBean : LitePalSupport() {
    var id: Long = 0
    val userInfo: UserInfoBean? = null
    val datetime: String = ""
    val message: String = ""
    var type: String = ""
    override fun toString(): String {
        return "MessageEvent(userInfo=$userInfo, datetime='$datetime', message='$message')"
    }
}
