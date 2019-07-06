package com.cyl.musicapi.baidu

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*



interface BaiduApiService {
    companion object {
        const val V1_TING = "v1/restserver/ting"

        const val SEARCH_CATALOGSUG = "baidu.ting.search.catalogSug"
        const val SEARCH_SUGGESTION = "baidu.ting.search.suggestion"
        const val SONG_LRC = "baidu.ting.song.lry "
        const val SONG_PLAY = "baidu.ting.song.play"
        const val GET_SONGINFO = "baidu.ting.song.getInfos"
        const val GET_ARTISTINFO = "baidu.ting.artist.getinfo"    //获取歌手信息
        const val GET_ARTISTSONGLIST = "baidu.ting.artist.getSongList" //获取歌手的歌曲列表
        const val GET_ARTISTALUBMLIST = "baidu.ting.artist.getAlbumList"   //获取歌手的专辑列表;
        const val GET_ALBUMINFO = "baidu.ting.album.getAlbumInfo"
        const val QUERY_MERGE = "baidu.ting.search.merge"
        const val GET_PLAY_MV = "baidu.ting.mv.playMV"

        const val GET_CATEGORY_LIST = "baidu.ting.radio.getCategoryList"
        const val GET_CHANNEL_SONG = "baidu.ting.radio.getChannelSong"

        const val GET_BILLCATEGORY = "baidu.ting.billboard.billCategory" //榜单
        const val GET_BILL_LIST = "baidu.ting.billboard.billList"

        const val PAGESIZE = 20

        fun getDownloadUrlBySongId(songId: String): String {
            return "http://ting.baidu.com/data/music/links?songIds=$songId"
        }
    }



    @GET("$V1_TING?version=5.6.5.0&method=$GET_CATEGORY_LIST")
    fun getRadioChannels(): Observable<RadioData>


    @GET
    fun getBaiduLyric(@Url baseUrl: String): Observable<ResponseBody>


    @GET("$V1_TING?method=$GET_ARTISTINFO")
    fun getArtistInfo(@Query("tinguid") tinguid: String,
                      @Query("artistid") artistid: String): Observable<BaiduArtistInfo>



    @GET("$V1_TING?method=$GET_BILLCATEGORY")
    fun getBillPlaylist(): Observable<BaiduList>


    @GET("$V1_TING?method=$GET_BILL_LIST")
    fun getBillMusicList(@Query("type") type: String,
                         @Query("size") size: Int,
                         @Query("offset") offset: Int): Observable<BaiduMusicList>


    @GET("$V1_TING?method=$SONG_PLAY")
    fun querySong(@Query("songid") songId: String): Observable<SongPlayRes>

        //http://musicapi.qianqian.com/v1/restserver/ting?method=baidu.ting.search.suggestion&query=Music
    @GET("$V1_TING?method=$SEARCH_SUGGESTION")
    fun getSearchSuggestion(@Query("query") query: String): Observable<Suggestion>

    @GET("$V1_TING?method=$SEARCH_CATALOGSUG")
    fun querySug(@Query("query") query: String): Observable<BaiduSearchSug>


    @GET("$V1_TING?method=$QUERY_MERGE")
    fun queryMerge(@Query("query") query: String,
                   @Query("page_no") pageNo: Int,
                   @Query("page_size") pageSize: Int): Observable<BaiduSearchMergeInfo>


    @GET("$V1_TING?version=5.6.5.0&method=$GET_CHANNEL_SONG")
    fun getRadioChannelSongs(
            @Query("channelname") channelName: String,
            @Query("pn") pn: Int = 0,
            @Query("rn") rn: Int = 10
    ): Observable<RadioChannelData>


    @GET("$V1_TING?from=qianqian&method=$GET_PLAY_MV")
    fun getPlayMv(@Query("song_id") songId: String?): Observable<BaiduPlayMv>

    @GET
    fun getTingSongInfo(@Url baseUrl: String): Observable<BaiduSongInfo>

    @GET
    fun getTingSongLink(@Url baseUrl: String): Observable<BaiduSongInfo>

    @Streaming
    @GET
    fun downloadFile(@Url downloadUrl: String, @HeaderMap params: Map<String, String>): Observable<ResponseBody>


    @GET("$V1_TING?method=$GET_ARTISTSONGLIST")
    fun getArtistSongList(@Query("tinguid") tinguid: String,
                          @Query("offset") offset: Int,
                          @Query("limits") limits: Int = PAGESIZE): Observable<ArtistMusicList>


    @GET("$V1_TING?method=$GET_ALBUMINFO")
    fun getAlbumInfo(@Query("album_id") albumId: String): Observable<AlbumSongList>


    @GET("$V1_TING?method=$SONG_LRC")
    fun queryLrc(@Query("songid") songId: String): Observable<BaiduLyric>

    @GET("$V1_TING?method=$GET_ARTISTALUBMLIST")
    fun getArtistAlbumList(@Query("tinguid") tinguid: String,
                           @Query("offset") offset: Int,
                           @Query("limits") limits: Int = PAGESIZE)
            : Observable<ArtistAlbumList>
}
