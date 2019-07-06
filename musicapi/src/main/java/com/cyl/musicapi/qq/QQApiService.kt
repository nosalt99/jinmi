package com.cyl.musicapi.qq

import com.cyl.musicapi.bean.ArtistsData
import com.cyl.musicapi.bean.ArtistsDataInfo
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Streaming
import retrofit2.http.Url



interface QQApiService {
    //http://c.y.qq.com/soso/fcgi-bin/search_cp?

    @Headers("referer: https://y.qq.com/portal/player.html")
    @GET("soso/fcgi-bin/search_cp?")
    fun searchByQQ(@QueryMap params: Map<String, String>): Observable<QQApiModel>

    @Headers("referer: https://y.qq.com/portal/player.html")
    @GET("/cgi-bin/musicu.fcg?")
    fun getQQArtists(@Query("data") data: String): Observable<ArtistsDataInfo>

    @Headers("referer: https://y.qq.com/portal/player.html")
    @GET("/cgi-bin/musicu.fcg?")
    fun getQQArtists1(@Query("data") data: String): Observable<ResponseBody>

}
