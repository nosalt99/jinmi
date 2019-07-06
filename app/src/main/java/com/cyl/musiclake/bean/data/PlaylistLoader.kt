package com.cyl.musiclake.bean.data

import com.cyl.musiclake.bean.Music
import com.cyl.musiclake.bean.Playlist
import com.cyl.musiclake.bean.data.db.DaoLitepal
import com.cyl.musiclake.common.Constants


object PlaylistLoader {
    private val TAG = "PlaylistLoader"


    fun getAllPlaylist(): MutableList<Playlist> {
        return DaoLitepal.getAllPlaylist()
    }


    fun getPlaylist(pid: String): Playlist {
        return DaoLitepal.getPlaylist(pid)
    }



    fun getHistoryPlaylist(): Playlist {
        return DaoLitepal.getPlaylist(Constants.PLAYLIST_HISTORY_ID)
    }


    fun getFavoritePlaylist(): Playlist {
        return DaoLitepal.getPlaylist(Constants.PLAYLIST_LOVE_ID)
    }


    fun createDefaultPlaylist(type: String, name: String): Boolean {
        return createPlaylist(type, type, name)
    }


    fun createPlaylist(pid: String, type: String, name: String): Boolean {
        val playlist = Playlist()
        playlist.pid = pid
        playlist.date = System.currentTimeMillis()
        playlist.updateDate = System.currentTimeMillis()
        playlist.name = name
        playlist.type = type
        if (type != Constants.PLAYLIST_QUEUE_ID)
            playlist.order = "updateDate desc"
        return DaoLitepal.saveOrUpdatePlaylist(playlist)
    }



    fun renamePlaylist(playlist: Playlist, name: String): Boolean {
        playlist.name = name
        return DaoLitepal.saveOrUpdatePlaylist(playlist)
    }



    fun getMusicForPlaylist(pid: String, order: String? = null): MutableList<Music> {
        return if (order == null) {
            DaoLitepal.getMusicList(pid)
        } else {
            DaoLitepal.getMusicList(pid, order)
        }
    }

    fun addMusicList(pid: String, musicList: List<Music>): Boolean {
        for (music in musicList) {
            addToPlaylist(pid, music)
        }
        return true
    }


    fun addToPlaylist(pid: String, music: Music): Boolean {
        try {
            return DaoLitepal.addToPlaylist(music, pid)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return false
    }


    fun removeSong(pid: String, mid: String) {
        try {
            DaoLitepal.removeSong(pid, mid)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }


    fun deletePlaylist(playlist: Playlist) {
        try {
            DaoLitepal.deletePlaylist(playlist)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }



    fun clearPlaylist(pid: String) {
        try {
            DaoLitepal.clearPlaylist(pid)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}
