package com.cyl.musiclake.api.playlist


import com.cyl.musicapi.playlist.CollectResult
import com.cyl.musicapi.playlist.MusicInfo
import com.cyl.musicapi.playlist.PlaylistInfo
import com.cyl.musicapi.playlist.UserInfo
import com.cyl.musiclake.bean.MessageInfoBean
import com.cyl.musiclake.bean.NoticeInfo
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*



interface PlaylistApiService {


    @GET
    fun checkMusicApiJs(@Url url: String): Observable<String>


    @GET
    fun checkMusicLakeNotice(@Url url: String): Observable<NoticeInfo>


    @GET("chat-history")
    fun getChatHistory(@Header("accesstoken") token: String?, @Query("start_dt") start: String?, @Query("end_dt") end: String?): Observable<MutableList<MessageInfoBean>>


    @GET("playlist")
    fun getOnlinePlaylist(@Header("accesstoken") token: String?): Observable<ResponseBody>


    @GET("playlist/{id}")
    fun getMusicList(@Header("accesstoken") token: String?, @Path("id") id: String): Observable<ResponseBody>


    @DELETE("playlist")
    fun deleteMusic(@Header("accesstoken") token: String?, @Query("id") id: String): Observable<ResponseBody>



    @PUT("playlist/{id}")
    @Headers("Content-Type: application/json")
    fun renameMusic(@Header("accesstoken") token: String?, @Path("id") id: String, @Body playlist: PlaylistInfo): Observable<ResponseBody>


    @POST("playlist")
    @Headers("Content-Type: application/json")
    fun createPlaylist(@Header("accesstoken") token: String?, @Body playlist: PlaylistInfo): Observable<ResponseBody>


    @POST("playlist/{id}")
    @Headers("Content-Type: application/json")
    fun collectMusic(@Header("accesstoken") token: String?, @Path("id") id: String, @Body musicInfo: MusicInfo): Observable<ResponseBody>


    @POST("playlist/{id}/batch")
    @Headers("Content-Type: application/json")
    fun collectBatchMusic(@Header("accesstoken") token: String?, @Path("id") id: String, @Body data: Any): Observable<CollectResult>


    @POST("playlist/{id}/batch2")
    @Headers("Content-Type: application/json")
    fun collectBatch2Music(@Header("accesstoken") token: String?, @Path("id") id: String, @Body data: Any): Observable<CollectResult>


    @DELETE("playlist/{id}")
    fun disCollectMusic(@Header("accesstoken") token: String?, @Path("id") id: String, @Query("id") songid: String): Observable<ResponseBody>


    @GET("auth/qq/android")
    fun loginByQQ(@Query("access_token") token: String?,
                  @Query("openid") openid: String): Observable<UserInfo>



    @GET("auth/weibo/android")
    fun loginByWeiBo(@Query("access_token") token: String?,
                     @Query("uid") openid: String): Observable<UserInfo>


    @GET("user")
    fun checkLoginStatus(@Header("accesstoken") token: String?): Observable<UserInfo>


    @GET("music/netease/rank")
    fun getNeteaseRank(@Query("ids[]") ids: IntArray, @Query("limit") limit: Int? = null): Observable<MutableList<PlaylistInfo>>


    @GET("music/qq/rank")
    fun getQQRank(@Query("limit") limit: Int? = null, @Query("ids[]") ids: IntArray? = null): Observable<MutableList<PlaylistInfo>>


}
