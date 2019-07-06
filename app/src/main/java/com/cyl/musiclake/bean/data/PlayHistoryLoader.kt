package com.cyl.musiclake.bean.data

import com.cyl.musiclake.common.Constants
import com.cyl.musiclake.bean.data.db.DaoLitepal
import com.cyl.musiclake.bean.Music


object PlayHistoryLoader {

    private val TAG = "PlayQueueLoader"


    fun addSongToHistory(music: Music) {
        try {
            DaoLitepal.addToPlaylist(music, Constants.PLAYLIST_HISTORY_ID)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }


    fun getPlayHistory(): MutableList<Music> {
        return DaoLitepal.getMusicList(Constants.PLAYLIST_HISTORY_ID, "updateDate desc")
    }


    fun clearPlayHistory() {
        try {
            DaoLitepal.clearPlaylist(Constants.PLAYLIST_HISTORY_ID)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}
