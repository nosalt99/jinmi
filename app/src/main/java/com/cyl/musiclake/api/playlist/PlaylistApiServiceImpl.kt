package com.cyl.musiclake.api.playlist

import com.cyl.musicapi.playlist.*
import com.cyl.musiclake.api.music.MusicApiServiceImpl
import com.cyl.musiclake.api.music.MusicUtils
import com.cyl.musiclake.bean.Music
import com.cyl.musiclake.bean.NoticeInfo
import com.cyl.musiclake.bean.Playlist
import com.cyl.musiclake.common.Constants
import com.cyl.musiclake.api.net.ApiManager
import com.cyl.musiclake.ui.my.user.User
import com.cyl.musiclake.ui.my.user.UserStatus
import com.cyl.musiclake.utils.SPUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe



object PlaylistApiServiceImpl {
    private val TAG = "PlaylistApiServiceImpl"
    val playlistApiService by lazy { ApiManager.getInstance().create(PlaylistApiService::class.java, SPUtils.getAnyByKey(SPUtils.SP_KEY_PLATER_API_URL, Constants.BASE_PLAYER_URL)) }

    val token: String?
        get() = UserStatus.getUserInfo()?.token


    fun getMusicLakeNotice(): Observable<NoticeInfo> {
        val url = "https://music-lake-android.zzsun.cc/notice.json"
        return playlistApiService.checkMusicLakeNotice(url)
    }

    fun getPlaylist(): Observable<MutableList<Playlist>> {
        return playlistApiService.getOnlinePlaylist(token)
                .flatMap { it ->
                    val json = it.string()
                    val data = Gson().fromJson<MutableList<PlaylistInfo>>(json, object : TypeToken<MutableList<PlaylistInfo>>() {
                    }.type)
                    val result = mutableListOf<Playlist>()
                    for (playlistInfo in data) {
                        val playlist = Playlist()
                        playlist.pid = playlistInfo.id
                        playlist.name = playlistInfo.name
                        playlist.total = playlistInfo.total.toLong()
                        playlist.coverUrl = playlistInfo.cover
                        playlist.type = Constants.PLAYLIST_CUSTOM_ID
                        result.add(playlist)
                    }
                    Observable.create(ObservableOnSubscribe<MutableList<Playlist>> {
                        if (data.isEmpty() && json.contains("msg")) {
                            val msg = Gson().fromJson<ErrorInfo>(json.toString(), ErrorInfo::class.java)
                            it.onError(Throwable(msg.msg))
                        } else {
                            it.onNext(result)
                            it.onComplete()
                        }
                    })
                }
    }


    fun getMusicList(pid: String): Observable<MutableList<Music>> {
        return playlistApiService.getMusicList(token, pid)
                .flatMap { it ->
                    val json = it.string()
                    val data = Gson().fromJson<MutableList<MusicInfo>>(json, object : TypeToken<MutableList<MusicInfo>>() {
                    }.type)
                    val musicList = mutableListOf<Music>()
                    for (musicInfo in data) {
                        val music = MusicUtils.getMusic(musicInfo)
                        musicList.add(music)
                    }
                    Observable.create(ObservableOnSubscribe<MutableList<Music>> {
                        if (data.isEmpty() && json.contains("msg")) {
                            val msg = Gson().fromJson<ErrorInfo>(json.toString(), ErrorInfo::class.java)
                            it.onError(Throwable(msg.msg))
                        } else {
                            it.onNext(musicList)
                            it.onComplete()
                        }
                    })
                }
    }


    fun getPlayListMusic(data: MutableList<Music>): Observable<MutableList<Music>> {
        return MusicApiServiceImpl.getAnyVendorSongDetail(data)
    }



    fun createPlaylist(name: String): Observable<Playlist> {
        val newPlaylistInfo = PlaylistInfo(name)
        return playlistApiService.createPlaylist(token, newPlaylistInfo)
                .flatMap { it ->
                    val json = it.string()
                    val data = Gson().fromJson<PlaylistInfo>(json.toString(), PlaylistInfo::class.java)
                    val playlist = Playlist(data?.id, data?.name)
                    Observable.create(ObservableOnSubscribe<Playlist> {
                        if (data == null && json.contains("msg")) {
                            val msg = Gson().fromJson<ErrorInfo>(json.toString(), ErrorInfo::class.java)
                            it.onError(Throwable(msg.msg))
                        } else {
                            it.onNext(playlist)
                            it.onComplete()
                        }
                    })
                }
    }


    fun deletePlaylist(pid: String): Observable<String> {
        return playlistApiService.deleteMusic(token, pid)
                .flatMap { it ->
                    val json = it.string()
                    val errorInfo = Gson().fromJson<ErrorInfo>(json.toString(), ErrorInfo::class.java)
                    Observable.create(ObservableOnSubscribe<String> {
                        if (errorInfo.msg.isEmpty()) {
                            it.onNext("歌单删除成功")
                            it.onComplete()
                        } else {
                            it.onError(Throwable(errorInfo.msg))
                        }
                    })
                }
    }


    fun renamePlaylist(pid: String, name: String): Observable<String> {
        val playlist = PlaylistInfo(name = name)
        return playlistApiService.renameMusic(token, pid, playlist)
                .flatMap { it ->
                    val json = it.string()
                    val errorInfo = Gson().fromJson<ErrorInfo>(json.toString(), ErrorInfo::class.java)
                    Observable.create(ObservableOnSubscribe<String> {
                        if (errorInfo.msg.isEmpty()) {
                            it.onNext("修改成功")
                            it.onComplete()
                        } else {
                            it.onError(Throwable(errorInfo.msg))
                        }
                    })
                }
    }


    fun collectMusic(pid: String, music: Music): Observable<String>? {
        val musicInfo = MusicUtils.getMusicInfo(music)
        return playlistApiService.collectMusic(token, pid, musicInfo)
                .flatMap { it ->
                    val json = it.string()
                    Observable.create(ObservableOnSubscribe<String> {
                        if (json == "{}") {
                            it.onNext("添加成功")
                            it.onComplete()
                        } else {
                            try {
                                val errorInfo = Gson().fromJson<ErrorInfo>(json.toString(), ErrorInfo::class.java)
                                it.onNext(errorInfo.msg)
                                it.onComplete()
                            } catch (e: Throwable) {
                                it.onError(Throwable(e.message))
                            }
                        }
                    })
                }
    }


    fun collectBatchMusic(pid: String, vendor: String, musics: MutableList<Music>?): Observable<String>? {
        val ids = mutableListOf<String>()
        musics?.forEach {
            ids.add(it.mid.toString())
        }
        return playlistApiService.collectBatchMusic(token, pid, CollectBatchBean(ids = ids, vendor = vendor))
                .flatMap { result ->
                    Observable.create(ObservableOnSubscribe<String> {
                        if (result.failedList != null) {
                            it.onNext("${musics!!.size - result.failedList!!.size}首添加成功,${result.failedList!!.size}首添加失败！")
                            it.onComplete()
                        } else {
                            it.onError(Throwable("添加失败"))
                        }
                    })
                }
    }



    fun collectBatch2Music(pid: String, musicList: MutableList<Music>?): Observable<String>? {
        val collects = mutableListOf<CollectDetail>()
        musicList?.forEach {
            it.type?.let { it1 -> it.mid?.let { it2 -> CollectDetail(it2, it1) } }?.let { it2 -> collects.add(it2) }
        }
        return playlistApiService.collectBatch2Music(token, pid, CollectBatch2Bean(collects))
                .flatMap { result ->
                    Observable.create(ObservableOnSubscribe<String> {
                        if (result.failedList != null) {
                            it.onNext("${musicList!!.size - result.failedList!!.size}首添加成功,${result.failedList!!.size}首添加失败！")
                            it.onComplete()
                        } else {
                            it.onError(Throwable("添加失败"))
                        }
                    })
                }
    }


    fun disCollectMusic(pid: String, music: Music): Observable<String> {
        return playlistApiService.disCollectMusic(token, pid, music.collectId.toString())
                .flatMap { it ->
                    val json = it.string()
                    val errorInfo = Gson().fromJson<ErrorInfo>(json.toString(), ErrorInfo::class.java)
                    Observable.create(ObservableOnSubscribe<String> {
                        if (errorInfo.msg.isEmpty()) {
                            it.onNext("已取消收藏")
                            it.onComplete()
                        } else {
                            it.onError(Throwable(errorInfo.msg))
                        }
                    })
                }
    }



    fun login(token: String, openid: String, method: String): Observable<User> {
        val observable = if (method == Constants.QQ) playlistApiService.loginByQQ(token, openid)
        else playlistApiService.loginByWeiBo(token, openid)
        return observable.flatMap { data ->
            val user = User()
            user.nick = data.nickname
            user.name = data.nickname
            user.avatar = data.avatar
            user.token = data.token
            Observable.create(ObservableOnSubscribe<User> {
                if (!user.token.isEmpty()) {
                    it.onNext(user)
                    it.onComplete()
                } else {
                    it.onError(Throwable("登录异常"))
                }
            })
        }
    }



    fun checkLoginStatus(): Observable<User> {
        return playlistApiService.checkLoginStatus(token)
                .flatMap { data ->
                    val user = User()
                    user.nick = data.nickname
                    user.name = data.nickname
                    user.avatar = data.avatar
                    Observable.create(ObservableOnSubscribe<User> {
                        if (user.name != null) {
                            it.onNext(user)
                            it.onComplete()
                        } else {
                            it.onError(Throwable(""))
                        }
                    })
                }
    }


    fun getRankDetailInfo(ids: IntArray, limit: Int? = null, type: String?): Observable<MutableList<Playlist>> {
        return if (type == Constants.PLAYLIST_WY_ID) getNeteaseRank(ids, limit)
        else getQQRank(limit, ids)
    }


    fun getNeteaseRank(ids: IntArray, limit: Int? = null): Observable<MutableList<Playlist>> {
        return playlistApiService.getNeteaseRank(ids, limit)
                .flatMap { data ->
                    val list = mutableListOf<Playlist>()
                    data.forEach {
                        val playlist = Playlist()
                        playlist.coverUrl = it.cover
                        playlist.des = it.description
                        playlist.pid = it.id
                        playlist.name = it.name
                        playlist.type = Constants.PLAYLIST_WY_ID
                        playlist.playCount = it.playCount
                        playlist.musicList = MusicUtils.getMusicList(it.list, Constants.NETEASE)
                        list.add(playlist)
                    }
                    Observable.create(ObservableOnSubscribe<MutableList<Playlist>> {
                        it.onNext(list)
                        it.onComplete()
                    })
                }
    }


    fun getQQRank(limit: Int? = null, ids: IntArray? = null): Observable<MutableList<Playlist>> {
        return playlistApiService.getQQRank(limit, ids)
                .flatMap { data ->
                    val list = mutableListOf<Playlist>()
                    data.forEach {
                        val playlist = Playlist()
                        playlist.coverUrl = it.cover
                        playlist.des = it.description
                        playlist.pid = it.id
                        playlist.name = it.name
                        playlist.type = Constants.PLAYLIST_QQ_ID
                        playlist.playCount = it.playCount
                        playlist.musicList = MusicUtils.getMusicList(it.list, Constants.QQ)
                        list.add(playlist)
                    }
                    Observable.create(ObservableOnSubscribe<MutableList<Playlist>> {
                        it.onNext(list)
                        it.onComplete()
                    })
                }
    }
}
