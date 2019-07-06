package com.cyl.musiclake.bean.data

import com.cyl.musiclake.common.Constants
import com.cyl.musiclake.bean.data.db.DaoLitepal
import com.cyl.musiclake.bean.Music
import org.jetbrains.anko.doAsync



object PlayQueueLoader {

    private val TAG = "PlayQueueLoader"


    fun getPlayQueue(): List<Music> {
        return DaoLitepal.getMusicList(Constants.PLAYLIST_QUEUE_ID)
    }


    fun updateQueue(musics: List<Music>) {
        doAsync {
            clearQueue()
            musics.forEach {
                DaoLitepal.addToPlaylist(it, Constants.PLAYLIST_QUEUE_ID)
            }
        }
    }


    fun clearQueue() {
        try {
            DaoLitepal.clearPlaylist(Constants.PLAYLIST_QUEUE_ID)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}
