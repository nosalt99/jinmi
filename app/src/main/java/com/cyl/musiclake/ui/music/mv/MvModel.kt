package com.cyl.musiclake.ui.music.mv

import com.cyl.musicapi.netease.*
import com.cyl.musiclake.api.music.baidu.BaiduApiServiceImpl
import com.cyl.musiclake.api.music.netease.NeteaseApiServiceImpl
import com.cyl.musiclake.bean.MvInfoBean
import com.cyl.musiclake.api.net.ApiManager
import com.cyl.musiclake.api.net.RequestCallBack



class MvModel {

    fun loadMvDetail(mvid: String?, result: RequestCallBack<MvDetailInfo>?) {
        val observable = mvid?.let { NeteaseApiServiceImpl.getMvDetailInfo(it) } ?: return
        ApiManager.request(observable, result)
    }


    fun loadRecentMv(limit: Int = 30, result: RequestCallBack<MvInfo>?) {
        val observable = NeteaseApiServiceImpl.getNewestMv(limit)
        ApiManager.request(observable, result)
    }


    fun loadMv(offset: Int, result: RequestCallBack<MvInfo>?) {
        val observable = NeteaseApiServiceImpl.getTopMv(50, offset)
        ApiManager.request(observable, result)
    }


    fun loadPersonalizedMv( result: RequestCallBack<MvInfo>?) {
        val observable = NeteaseApiServiceImpl.personalizedMv()
        ApiManager.request(observable, result)
    }


    fun loadSimilarMv(mvid: String?, result: RequestCallBack<SimilarMvInfo>?) {
        val observable = mvid?.let { NeteaseApiServiceImpl.getSimilarMv(it) } ?: return
        ApiManager.request(observable, result)
    }


    fun loadMvComment(mvid: String?, result: RequestCallBack<MvComment>?) {
        val observable = mvid?.let { NeteaseApiServiceImpl.getMvComment(it) } ?: return
        ApiManager.request(observable, result)
    }


    fun searchMv(key: String, offset: Int, result: RequestCallBack<SearchInfo>?) {
        val observable = NeteaseApiServiceImpl.searchMoreInfo(key, 30, offset, 1004)
        ApiManager.request(observable, result)
    }

    fun loadBaiduMv(songId: String?, result: RequestCallBack<MvInfoBean>?) {
        val observable = BaiduApiServiceImpl.getMvInfo(songId)
        ApiManager.request(observable, result)
    }
}