package com.cyl.musiclake.api;

import com.cyl.musicapi.qq.QQApiModel;
import com.cyl.musiclake.common.Constants;
import com.google.gson.Gson;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ApiManagerTest extends TestCase {

    public void testQQ() throws Exception {

//        Scanner in = new Scanner(System.in);
        System.out.println("------------start load---------------");
        System.out.println("Please Input Key Words :");
        String key = "薛之谦";

        Map<String, String> params = new HashMap<>();
        params.put("p", "1"); //page
        params.put("n", "10");//limit
        params.put("w", key);// key
        params.put("aggr", "1");
        params.put("cr", "1");
        params.put("lossless", "1");
        params.put("format", "json");
        System.out.println("Result--");
        String result = FetchUtils.getDataFromUrl("http://c.y.qq.com/soso/fcgi-bin/search_cp?", params);
        System.out.println(result);
        System.out.println("------------ end ---------------");

        System.out.println("------------start parse---------------");
        Gson gson = new Gson();
        QQApiModel qqApi = gson.fromJson(result, QQApiModel.class);
        System.out.println(qqApi.getCode());
        System.out.println(qqApi.getTime());
        System.out.println(qqApi.getData().getKeyword());
        System.out.println(qqApi.getData().getSong().getTotalnum());
        System.out.println(qqApi.getData().getSong().getCurpage());
        System.out.println(qqApi.getData().getSong().getCurnum());
        System.out.println(qqApi.getData().getSong().getList().size());

        if (qqApi.getData().getSong().getList().size() > 0) {
            System.out.println(qqApi.getData().getSong().getList().get(0).toString());

            System.out.println("------------ song ---------------");
            System.out.println(qqApi.getData().getSong().getList().get(0).getSongmid());
            System.out.println(qqApi.getData().getSong().getList().get(0).getSongname());

            System.out.println(qqApi.getData().getSong().getList().get(0).getPay().getPayplay());
            System.out.println(qqApi.getData().getSong().getList().get(0).getSinger().toString());
        }
        System.out.println("------------ end ---------------");


    }

    public void testXiami() throws Exception {

        System.out.println("------------start 搜索 薛之谦---------------");
        Map<String, String> params = new HashMap<>();
        params.put("referer", "http://h.xiami.com/"); //page
        String url = "http://api.xiami.com/web?v='2.0'&key=薛之谦&limit=10&page=1&r=search/songs&app_key=1";
        String result = FetchUtils.getDataWithHeader(url, params);
        System.out.println(result);
        System.out.println("------------ end ---------------");
        Gson gson = new Gson();
        XiamiApiModel xiamiApiModel = gson.fromJson(result, XiamiApiModel.class);

        System.out.println("------------start 获取歌曲详细信息---------------");
        //获取歌曲详细信息
        int songId = xiamiApiModel.getData().getSongs().get(0).getSong_id();
        String ss = xiamiApiModel.getData().getSongs().get(0).toString();
        System.out.println(songId);
        System.out.println(ss);
        String url1 = "http://www.xiami.com/song/playlist/id/" + songId + "/object_name/default/object_id/0/cat/json";
        result = FetchUtils.getDataWithHeader(url1, params);
        System.out.println(result);
        System.out.println("------------ end ---------------");
        //解析播放地址
        System.out.println("------------ 解析播放地址 ---------------");
        XiamiLyricInfo info = gson.fromJson(result,XiamiLyricInfo.class);
        System.out.println(info.data.trackList.get(0).getSongName());
        System.out.println(info.getData().getTrackList().get(0).getLocation());
        System.out.println("------------ 解析 ---------------");
        String xx =info.data.trackList.get(0).getLocation();
        System.out.println(Decode.parseLocation(xx).replace("^","0"));

    }

    public void testOnline() throws Exception {
        System.out.println("xxxxxxxxxxx");
        String result = FetchUtils.getDataFromUrl(Constants.BASE_MUSIC_URL);
        System.out.println(result);
    }

    class XiamiApiModel {

        private int state;
        private String message;
        private String request_id;
        private DataBean data;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public class DataBean {


            private int total;
            private int previous;
            private int next;
            private List<SongsBean> songs;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPrevious() {
                return previous;
            }

            public void setPrevious(int previous) {
                this.previous = previous;
            }

            public int getNext() {
                return next;
            }

            public void setNext(int next) {
                this.next = next;
            }

            public List<SongsBean> getSongs() {
                return songs;
            }

            public void setSongs(List<SongsBean> songs) {
                this.songs = songs;
            }

            public class SongsBean {
                @Override
                public String toString() {
                    return "SongsBean{" +
                            "song_id=" + song_id +
                            ", song_name='" + song_name + '\n' +
                            ", album_id=" + album_id +
                            ", album_name='" + album_name + '\n' +
                            ", album_logo='" + album_logo + '\n' +
                            ", artist_id=" + artist_id +
                            ", artist_name='" + artist_name + '\n' +
                            ", artist_logo='" + artist_logo + '\n' +
                            ", listen_file='" + listen_file + '\n' +
                            ", demo=" + demo +
                            ", need_pay_flag=" + need_pay_flag +
                            ", lyric='" + lyric + '\n' +
                            ", is_play=" + is_play +
                            ", play_counts=" + play_counts +
                            ", singer='" + singer + '\n' +
                            ", purview_roles=" + purview_roles +
                            '}';
                }




                private int song_id;
                private String song_name;
                private int album_id;
                private String album_name;
                private String album_logo;
                private int artist_id;
                private String artist_name;
                private String artist_logo;
                private String listen_file;
                private int demo;
                private int need_pay_flag;
                private String lyric;
                private int is_play;
                private int play_counts;
                private String singer;
                private List<PurviewRolesBean> purview_roles;

                public int getSong_id() {
                    return song_id;
                }

                public void setSong_id(int song_id) {
                    this.song_id = song_id;
                }

                public String getSong_name() {
                    return song_name;
                }

                public void setSong_name(String song_name) {
                    this.song_name = song_name;
                }

                public int getAlbum_id() {
                    return album_id;
                }

                public void setAlbum_id(int album_id) {
                    this.album_id = album_id;
                }

                public String getAlbum_name() {
                    return album_name;
                }

                public void setAlbum_name(String album_name) {
                    this.album_name = album_name;
                }

                public String getAlbum_logo() {
                    return album_logo;
                }

                public void setAlbum_logo(String album_logo) {
                    this.album_logo = album_logo;
                }

                public int getArtist_id() {
                    return artist_id;
                }

                public void setArtist_id(int artist_id) {
                    this.artist_id = artist_id;
                }

                public String getArtist_name() {
                    return artist_name;
                }

                public void setArtist_name(String artist_name) {
                    this.artist_name = artist_name;
                }

                public String getArtist_logo() {
                    return artist_logo;
                }

                public void setArtist_logo(String artist_logo) {
                    this.artist_logo = artist_logo;
                }

                public String getListen_file() {
                    return listen_file;
                }

                public void setListen_file(String listen_file) {
                    this.listen_file = listen_file;
                }

                public int getDemo() {
                    return demo;
                }

                public void setDemo(int demo) {
                    this.demo = demo;
                }

                public int getNeed_pay_flag() {
                    return need_pay_flag;
                }

                public void setNeed_pay_flag(int need_pay_flag) {
                    this.need_pay_flag = need_pay_flag;
                }

                public String getLyric() {
                    return lyric;
                }

                public void setLyric(String lyric) {
                    this.lyric = lyric;
                }

                public int getIs_play() {
                    return is_play;
                }

                public void setIs_play(int is_play) {
                    this.is_play = is_play;
                }

                public int getPlay_counts() {
                    return play_counts;
                }

                public void setPlay_counts(int play_counts) {
                    this.play_counts = play_counts;
                }

                public String getSinger() {
                    return singer;
                }

                public void setSinger(String singer) {
                    this.singer = singer;
                }

                public List<PurviewRolesBean> getPurview_roles() {
                    return purview_roles;
                }

                public void setPurview_roles(List<PurviewRolesBean> purview_roles) {
                    this.purview_roles = purview_roles;
                }

                public class PurviewRolesBean {


                    private String quality;
                    private List<OperationListBean> operation_list;

                    public String getQuality() {
                        return quality;
                    }

                    public void setQuality(String quality) {
                        this.quality = quality;
                    }

                    public List<OperationListBean> getOperation_list() {
                        return operation_list;
                    }

                    public void setOperation_list(List<OperationListBean> operation_list) {
                        this.operation_list = operation_list;
                    }

                    public class OperationListBean {


                        private int purpose;
                        private int upgrade_role;

                        public int getPurpose() {
                            return purpose;
                        }

                        public void setPurpose(int purpose) {
                            this.purpose = purpose;
                        }

                        public int getUpgrade_role() {
                            return upgrade_role;
                        }

                        public void setUpgrade_role(int upgrade_role) {
                            this.upgrade_role = upgrade_role;
                        }
                    }
                }
            }
        }
    }

    class XiamiLyricInfo {



        private boolean status;
        private Object message;
        private DataBean data;
        private Object jumpurl;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public Object getJumpurl() {
            return jumpurl;
        }

        public void setJumpurl(Object jumpurl) {
            this.jumpurl = jumpurl;
        }

        public class DataBean {


            private int lastSongId;
            private String type;
            private int type_id;
            private Object clearlist;
            private int vip;
            private int vip_role;
            private int hqset;
            private List<TrackListBean> trackList;

            public int getLastSongId() {
                return lastSongId;
            }

            public void setLastSongId(int lastSongId) {
                this.lastSongId = lastSongId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getType_id() {
                return type_id;
            }

            public void setType_id(int type_id) {
                this.type_id = type_id;
            }

            public Object getClearlist() {
                return clearlist;
            }

            public void setClearlist(Object clearlist) {
                this.clearlist = clearlist;
            }

            public int getVip() {
                return vip;
            }

            public void setVip(int vip) {
                this.vip = vip;
            }

            public int getVip_role() {
                return vip_role;
            }

            public void setVip_role(int vip_role) {
                this.vip_role = vip_role;
            }

            public int getHqset() {
                return hqset;
            }

            public void setHqset(int hqset) {
                this.hqset = hqset;
            }

            public List<TrackListBean> getTrackList() {
                return trackList;
            }

            public void setTrackList(List<TrackListBean> trackList) {
                this.trackList = trackList;
            }

            public class TrackListBean {
                @Override
                public String toString() {
                    return "TrackListBean{" +
                            "songId='" + songId + '\n' +
                            ", songName='" + songName + '\n' +
                            ", subName='" + subName + '\n' +
                            ", newSubName='" + newSubName + '\n' +
                            ", translation='" + translation + '\n' +
                            ", albumId=" + albumId +
                            ", artistId=" + artistId +
                            ", singers='" + singers + '\n' +
                            ", mvId=" + mvId +
                            ", cdSerial=" + cdSerial +
                            ", track=" + track +
                            ", pinyin='" + pinyin + '\n' +
                            ", bakSongId=" + bakSongId +
                            ", panFlag=" + panFlag +
                            ", musicType='" + musicType + '\n' +
                            ", bakSong=" + bakSong +
                            ", lyricInfo=" + lyricInfo +
                            ", songwriters='" + songwriters + '\n' +
                            ", composer='" + composer + '\n' +
                            '}';
                }




                private String songId;
                private String songName;
                private String subName;
                private String newSubName;
                private String translation;
                private int albumId;
                private int artistId;
                private String singers;
                private int mvId;
                private int cdSerial;
                private int track;
                private String pinyin;
                private int bakSongId;
                private int panFlag;
                private String musicType;
                private Object bakSong;
                private LyricInfoBean lyricInfo;
                private String songwriters;
                private String composer;
                private String arrangement;
                private int boughtCount;
                private int pace;
                private String albumLanguage;
                private int playCount;
                private boolean offline;
                private int offlineType;
                private boolean downloadCount;
                private boolean originOffline;
                private boolean exclusive;
                private int recommendCount;
                private int collectCount;
                private String songStringId;
                private String albumStringId;
                private String artistStringId;
                private long demoCreateTime;
                private boolean offLyric;
                private String artist;
                private String songOpt;
                private PurviewsBean purviews;
                private String publishStatus;
                private boolean demo;
                private int s;
                private String song_id;
                private int album_id;
                private String name;
                private String title;
                private String album_name;
                private String sub_title;
                private String new_sub_title;
                private String song_sub_title;
                private String artist_name;
                private int artist_id;
                private int cd_serial;
                private int length;
                private int recommends;
                private int collects;
                private Object key_words;
                private int music_type;
                private int play_volume;
                private Object flag;
                private String album_logo;
                private int needpay;
                private String mvUrl;
                private int playstatus;
                private int downloadstatus;
                private String downloadjson;
                private int can_show;
                private int can_check;
                private String location;
                private String lyric;
                private String lyric_url;
                private String object_id;
                private String object_name;
                private int insert_type;
                private String background;
                private String aritst_type;
                private String artist_url;
                private int grade;
                private int tryhq;
                private String pic;
                private String album_pic;
                private String rec_note;
                private List<String> bizTags;
                private List<Integer> singerIds;
                private List<String> tags;
                private List<SingersSourceBean> singersSource;

                public String getSongId() {
                    return songId;
                }

                public void setSongId(String songId) {
                    this.songId = songId;
                }

                public String getSongName() {
                    return songName;
                }

                public void setSongName(String songName) {
                    this.songName = songName;
                }

                public String getSubName() {
                    return subName;
                }

                public void setSubName(String subName) {
                    this.subName = subName;
                }

                public String getNewSubName() {
                    return newSubName;
                }

                public void setNewSubName(String newSubName) {
                    this.newSubName = newSubName;
                }

                public String getTranslation() {
                    return translation;
                }

                public void setTranslation(String translation) {
                    this.translation = translation;
                }

                public int getAlbumId() {
                    return albumId;
                }

                public void setAlbumId(int albumId) {
                    this.albumId = albumId;
                }

                public int getArtistId() {
                    return artistId;
                }

                public void setArtistId(int artistId) {
                    this.artistId = artistId;
                }

                public String getSingers() {
                    return singers;
                }

                public void setSingers(String singers) {
                    this.singers = singers;
                }

                public int getMvId() {
                    return mvId;
                }

                public void setMvId(int mvId) {
                    this.mvId = mvId;
                }

                public int getCdSerial() {
                    return cdSerial;
                }

                public void setCdSerial(int cdSerial) {
                    this.cdSerial = cdSerial;
                }

                public int getTrack() {
                    return track;
                }

                public void setTrack(int track) {
                    this.track = track;
                }

                public String getPinyin() {
                    return pinyin;
                }

                public void setPinyin(String pinyin) {
                    this.pinyin = pinyin;
                }

                public int getBakSongId() {
                    return bakSongId;
                }

                public void setBakSongId(int bakSongId) {
                    this.bakSongId = bakSongId;
                }

                public int getPanFlag() {
                    return panFlag;
                }

                public void setPanFlag(int panFlag) {
                    this.panFlag = panFlag;
                }

                public String getMusicType() {
                    return musicType;
                }

                public void setMusicType(String musicType) {
                    this.musicType = musicType;
                }

                public Object getBakSong() {
                    return bakSong;
                }

                public void setBakSong(Object bakSong) {
                    this.bakSong = bakSong;
                }

                public LyricInfoBean getLyricInfo() {
                    return lyricInfo;
                }

                public void setLyricInfo(LyricInfoBean lyricInfo) {
                    this.lyricInfo = lyricInfo;
                }

                public String getSongwriters() {
                    return songwriters;
                }

                public void setSongwriters(String songwriters) {
                    this.songwriters = songwriters;
                }

                public String getComposer() {
                    return composer;
                }

                public void setComposer(String composer) {
                    this.composer = composer;
                }

                public String getArrangement() {
                    return arrangement;
                }

                public void setArrangement(String arrangement) {
                    this.arrangement = arrangement;
                }

                public int getBoughtCount() {
                    return boughtCount;
                }

                public void setBoughtCount(int boughtCount) {
                    this.boughtCount = boughtCount;
                }

                public int getPace() {
                    return pace;
                }

                public void setPace(int pace) {
                    this.pace = pace;
                }

                public String getAlbumLanguage() {
                    return albumLanguage;
                }

                public void setAlbumLanguage(String albumLanguage) {
                    this.albumLanguage = albumLanguage;
                }

                public int getPlayCount() {
                    return playCount;
                }

                public void setPlayCount(int playCount) {
                    this.playCount = playCount;
                }

                public boolean isOffline() {
                    return offline;
                }

                public void setOffline(boolean offline) {
                    this.offline = offline;
                }

                public int getOfflineType() {
                    return offlineType;
                }

                public void setOfflineType(int offlineType) {
                    this.offlineType = offlineType;
                }

                public boolean isDownloadCount() {
                    return downloadCount;
                }

                public void setDownloadCount(boolean downloadCount) {
                    this.downloadCount = downloadCount;
                }

                public boolean isOriginOffline() {
                    return originOffline;
                }

                public void setOriginOffline(boolean originOffline) {
                    this.originOffline = originOffline;
                }

                public boolean isExclusive() {
                    return exclusive;
                }

                public void setExclusive(boolean exclusive) {
                    this.exclusive = exclusive;
                }

                public int getRecommendCount() {
                    return recommendCount;
                }

                public void setRecommendCount(int recommendCount) {
                    this.recommendCount = recommendCount;
                }

                public int getCollectCount() {
                    return collectCount;
                }

                public void setCollectCount(int collectCount) {
                    this.collectCount = collectCount;
                }

                public String getSongStringId() {
                    return songStringId;
                }

                public void setSongStringId(String songStringId) {
                    this.songStringId = songStringId;
                }

                public String getAlbumStringId() {
                    return albumStringId;
                }

                public void setAlbumStringId(String albumStringId) {
                    this.albumStringId = albumStringId;
                }

                public String getArtistStringId() {
                    return artistStringId;
                }

                public void setArtistStringId(String artistStringId) {
                    this.artistStringId = artistStringId;
                }

                public long getDemoCreateTime() {
                    return demoCreateTime;
                }

                public void setDemoCreateTime(long demoCreateTime) {
                    this.demoCreateTime = demoCreateTime;
                }

                public boolean isOffLyric() {
                    return offLyric;
                }

                public void setOffLyric(boolean offLyric) {
                    this.offLyric = offLyric;
                }

                public String getArtist() {
                    return artist;
                }

                public void setArtist(String artist) {
                    this.artist = artist;
                }

                public String getSongOpt() {
                    return songOpt;
                }

                public void setSongOpt(String songOpt) {
                    this.songOpt = songOpt;
                }

                public PurviewsBean getPurviews() {
                    return purviews;
                }

                public void setPurviews(PurviewsBean purviews) {
                    this.purviews = purviews;
                }

                public String getPublishStatus() {
                    return publishStatus;
                }

                public void setPublishStatus(String publishStatus) {
                    this.publishStatus = publishStatus;
                }

                public boolean isDemo() {
                    return demo;
                }

                public void setDemo(boolean demo) {
                    this.demo = demo;
                }

                public int getS() {
                    return s;
                }

                public void setS(int s) {
                    this.s = s;
                }

                public String getSong_id() {
                    return song_id;
                }

                public void setSong_id(String song_id) {
                    this.song_id = song_id;
                }

                public int getAlbum_id() {
                    return album_id;
                }

                public void setAlbum_id(int album_id) {
                    this.album_id = album_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getAlbum_name() {
                    return album_name;
                }

                public void setAlbum_name(String album_name) {
                    this.album_name = album_name;
                }

                public String getSub_title() {
                    return sub_title;
                }

                public void setSub_title(String sub_title) {
                    this.sub_title = sub_title;
                }

                public String getNew_sub_title() {
                    return new_sub_title;
                }

                public void setNew_sub_title(String new_sub_title) {
                    this.new_sub_title = new_sub_title;
                }

                public String getSong_sub_title() {
                    return song_sub_title;
                }

                public void setSong_sub_title(String song_sub_title) {
                    this.song_sub_title = song_sub_title;
                }

                public String getArtist_name() {
                    return artist_name;
                }

                public void setArtist_name(String artist_name) {
                    this.artist_name = artist_name;
                }

                public int getArtist_id() {
                    return artist_id;
                }

                public void setArtist_id(int artist_id) {
                    this.artist_id = artist_id;
                }

                public int getCd_serial() {
                    return cd_serial;
                }

                public void setCd_serial(int cd_serial) {
                    this.cd_serial = cd_serial;
                }

                public int getLength() {
                    return length;
                }

                public void setLength(int length) {
                    this.length = length;
                }

                public int getRecommends() {
                    return recommends;
                }

                public void setRecommends(int recommends) {
                    this.recommends = recommends;
                }

                public int getCollects() {
                    return collects;
                }

                public void setCollects(int collects) {
                    this.collects = collects;
                }

                public Object getKey_words() {
                    return key_words;
                }

                public void setKey_words(Object key_words) {
                    this.key_words = key_words;
                }

                public int getMusic_type() {
                    return music_type;
                }

                public void setMusic_type(int music_type) {
                    this.music_type = music_type;
                }

                public int getPlay_volume() {
                    return play_volume;
                }

                public void setPlay_volume(int play_volume) {
                    this.play_volume = play_volume;
                }

                public Object getFlag() {
                    return flag;
                }

                public void setFlag(Object flag) {
                    this.flag = flag;
                }

                public String getAlbum_logo() {
                    return album_logo;
                }

                public void setAlbum_logo(String album_logo) {
                    this.album_logo = album_logo;
                }

                public int getNeedpay() {
                    return needpay;
                }

                public void setNeedpay(int needpay) {
                    this.needpay = needpay;
                }

                public String getMvUrl() {
                    return mvUrl;
                }

                public void setMvUrl(String mvUrl) {
                    this.mvUrl = mvUrl;
                }

                public int getPlaystatus() {
                    return playstatus;
                }

                public void setPlaystatus(int playstatus) {
                    this.playstatus = playstatus;
                }

                public int getDownloadstatus() {
                    return downloadstatus;
                }

                public void setDownloadstatus(int downloadstatus) {
                    this.downloadstatus = downloadstatus;
                }

                public String getDownloadjson() {
                    return downloadjson;
                }

                public void setDownloadjson(String downloadjson) {
                    this.downloadjson = downloadjson;
                }

                public int getCan_show() {
                    return can_show;
                }

                public void setCan_show(int can_show) {
                    this.can_show = can_show;
                }

                public int getCan_check() {
                    return can_check;
                }

                public void setCan_check(int can_check) {
                    this.can_check = can_check;
                }

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public String getLyric() {
                    return lyric;
                }

                public void setLyric(String lyric) {
                    this.lyric = lyric;
                }

                public String getLyric_url() {
                    return lyric_url;
                }

                public void setLyric_url(String lyric_url) {
                    this.lyric_url = lyric_url;
                }

                public String getObject_id() {
                    return object_id;
                }

                public void setObject_id(String object_id) {
                    this.object_id = object_id;
                }

                public String getObject_name() {
                    return object_name;
                }

                public void setObject_name(String object_name) {
                    this.object_name = object_name;
                }

                public int getInsert_type() {
                    return insert_type;
                }

                public void setInsert_type(int insert_type) {
                    this.insert_type = insert_type;
                }

                public String getBackground() {
                    return background;
                }

                public void setBackground(String background) {
                    this.background = background;
                }

                public String getAritst_type() {
                    return aritst_type;
                }

                public void setAritst_type(String aritst_type) {
                    this.aritst_type = aritst_type;
                }

                public String getArtist_url() {
                    return artist_url;
                }

                public void setArtist_url(String artist_url) {
                    this.artist_url = artist_url;
                }

                public int getGrade() {
                    return grade;
                }

                public void setGrade(int grade) {
                    this.grade = grade;
                }

                public int getTryhq() {
                    return tryhq;
                }

                public void setTryhq(int tryhq) {
                    this.tryhq = tryhq;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getAlbum_pic() {
                    return album_pic;
                }

                public void setAlbum_pic(String album_pic) {
                    this.album_pic = album_pic;
                }

                public String getRec_note() {
                    return rec_note;
                }

                public void setRec_note(String rec_note) {
                    this.rec_note = rec_note;
                }

                public List<String> getBizTags() {
                    return bizTags;
                }

                public void setBizTags(List<String> bizTags) {
                    this.bizTags = bizTags;
                }

                public List<Integer> getSingerIds() {
                    return singerIds;
                }

                public void setSingerIds(List<Integer> singerIds) {
                    this.singerIds = singerIds;
                }

                public List<String> getTags() {
                    return tags;
                }

                public void setTags(List<String> tags) {
                    this.tags = tags;
                }

                public List<SingersSourceBean> getSingersSource() {
                    return singersSource;
                }

                public void setSingersSource(List<SingersSourceBean> singersSource) {
                    this.singersSource = singersSource;
                }

                public class LyricInfoBean {
                    @Override
                    public String toString() {
                        return "LyricInfoBean{" +
                                "lyricId=" + lyricId +
                                ", lyricType=" + lyricType +
                                ", lyricFile='" + lyricFile + '\n' +
                                ", isOfficial=" + isOfficial +
                                '}';
                    }



                    private int lyricId;
                    private int lyricType;
                    private String lyricFile;
                    private boolean isOfficial;

                    public int getLyricId() {
                        return lyricId;
                    }

                    public void setLyricId(int lyricId) {
                        this.lyricId = lyricId;
                    }

                    public int getLyricType() {
                        return lyricType;
                    }

                    public void setLyricType(int lyricType) {
                        this.lyricType = lyricType;
                    }

                    public String getLyricFile() {
                        return lyricFile;
                    }

                    public void setLyricFile(String lyricFile) {
                        this.lyricFile = lyricFile;
                    }

                    public boolean isIsOfficial() {
                        return isOfficial;
                    }

                    public void setIsOfficial(boolean isOfficial) {
                        this.isOfficial = isOfficial;
                    }
                }

                public class PurviewsBean {


                    private LISTENBean LISTEN;
                    private DOWNLOADBean DOWNLOAD;

                    public LISTENBean getLISTEN() {
                        return LISTEN;
                    }

                    public void setLISTEN(LISTENBean LISTEN) {
                        this.LISTEN = LISTEN;
                    }

                    public DOWNLOADBean getDOWNLOAD() {
                        return DOWNLOAD;
                    }

                    public void setDOWNLOAD(DOWNLOADBean DOWNLOAD) {
                        this.DOWNLOAD = DOWNLOAD;
                    }

                    public class LISTENBean {


                        private String LOW;
                        private String HIGH;

                        public String getLOW() {
                            return LOW;
                        }

                        public void setLOW(String LOW) {
                            this.LOW = LOW;
                        }

                        public String getHIGH() {
                            return HIGH;
                        }

                        public void setHIGH(String HIGH) {
                            this.HIGH = HIGH;
                        }
                    }

                    public class DOWNLOADBean {


                        private String LOW;
                        private String HIGH;

                        public String getLOW() {
                            return LOW;
                        }

                        public void setLOW(String LOW) {
                            this.LOW = LOW;
                        }

                        public String getHIGH() {
                            return HIGH;
                        }

                        public void setHIGH(String HIGH) {
                            this.HIGH = HIGH;
                        }
                    }
                }

                public class SingersSourceBean {


                    private int artistId;
                    private String artistStringId;
                    private String artistName;
                    private String artistLogo;
                    private int albumCount;
                    private String area;
                    private String alias;
                    private String pinyin;
                    private boolean isMusician;
                    private boolean isShow;
                    private String description;

                    public int getArtistId() {
                        return artistId;
                    }

                    public void setArtistId(int artistId) {
                        this.artistId = artistId;
                    }

                    public String getArtistStringId() {
                        return artistStringId;
                    }

                    public void setArtistStringId(String artistStringId) {
                        this.artistStringId = artistStringId;
                    }

                    public String getArtistName() {
                        return artistName;
                    }

                    public void setArtistName(String artistName) {
                        this.artistName = artistName;
                    }

                    public String getArtistLogo() {
                        return artistLogo;
                    }

                    public void setArtistLogo(String artistLogo) {
                        this.artistLogo = artistLogo;
                    }

                    public int getAlbumCount() {
                        return albumCount;
                    }

                    public void setAlbumCount(int albumCount) {
                        this.albumCount = albumCount;
                    }

                    public String getArea() {
                        return area;
                    }

                    public void setArea(String area) {
                        this.area = area;
                    }

                    public String getAlias() {
                        return alias;
                    }

                    public void setAlias(String alias) {
                        this.alias = alias;
                    }

                    public String getPinyin() {
                        return pinyin;
                    }

                    public void setPinyin(String pinyin) {
                        this.pinyin = pinyin;
                    }

                    public boolean isIsMusician() {
                        return isMusician;
                    }

                    public void setIsMusician(boolean isMusician) {
                        this.isMusician = isMusician;
                    }

                    public boolean isIsShow() {
                        return isShow;
                    }

                    public void setIsShow(boolean isShow) {
                        this.isShow = isShow;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }
                }
            }
        }
    }
}