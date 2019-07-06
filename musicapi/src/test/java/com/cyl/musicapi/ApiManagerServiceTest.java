package com.cyl.musicapi;

import com.cyl.musicapi.qq.QQApiServiceImpl;
import com.cyl.musicapi.xiami.XiamiServiceImpl;
import com.cyl.musiclake.bean.Music;
import com.google.gson.Gson;

import junit.framework.TestCase;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

import io.reactivex.Observable;


public class ApiManagerServiceTest extends TestCase {
    public void testsearch() {
        String key = "薛之谦";
        int limit = 8;
        int page = 1;
        Observable.merge(QQApiServiceImpl.search(key, limit, page),
                XiamiServiceImpl.search(key, limit, page))
                .subscribe(musicList -> {
                    System.out.println(musicList.size());
                    for (Music mus :
                            musicList) {
                        System.out.println(mus.toString());
                    }
                });
    }

    public void testQQ() {
        Gson gson = new Gson();
        //获取单个歌曲信息
        String mid = "002BWGZQ2UKjKn";
        String sizekey = "";
        String xx = "http://c.y.qq.com/v8/fcg-bin/fcg_play_single_song.fcg?json=3&songmid=" + mid + "&format=json";
        String xxjson = FetchUtils.getDataFromUrl(xx);
        FileSize fileSize = gson.fromJson(xxjson, FileSize.class);
        if (fileSize.getData().get(0).getFile().getSize_320mp3() != 0) {
            sizekey = "size320";
        } else if (fileSize.getData().get(0).getFile().getSize_128mp3() != 0) {
            sizekey = "size128";
        }
        //获取Key
        double guid = Math.floor(Math.random() * 1000000000);
        System.out.println(guid);
        String url = "https://c.y.qq.com/base/fcgi-bin/fcg_musicexpress.fcg?json=3&guid=" + guid + "&format=json";
        System.out.println(url);
        String json = FetchUtils.getDataFromUrl(url);
        System.out.println(json);
        Key mKey = gson.fromJson(json, Key.class);
        System.out.println(mKey.key);
        String perfix = "";
        if (sizekey.equals("size128")) {
            perfix = "M500"; //正常
        } else if (sizekey.equals("size320")) {
            perfix = "M800"; //高质量
        }
        System.out.println("----------songUrl------------");
        String songUrl = "http://dl.stream.qqmusic.qq.com/" +
                perfix + mid + ".mp3?vkey=" + mKey.key + "&guid=" + guid + "&fromtag=30";
        System.out.println(songUrl);

        //获取歌词
        System.out.println("----------lyric------------");
        String Lyric = "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?songmid=" + mid + "&g_tk=5381&loginUin=0&hostUin=0&format=json&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0";
        String result = FetchUtils.getDataWithHeader(Lyric);
        System.out.println(result);
        result = result.substring(result.indexOf("{"), result.lastIndexOf(")"));
        System.out.println(result);
        LyricInfo lyricInfo = gson.fromJson(result, LyricInfo.class);
        System.out.println(lyricInfo.getLyric());
        byte[] asByte = Base64.getDecoder().decode(lyricInfo.getLyric());
        try {
            System.out.println(new String(asByte, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private class Key {
        String key;
    }

    private class LyricInfo {



        private int retcode;
        private int code;
        private int subcode;
        private String lyric;
        private String trans;

        public int getRetcode() {
            return retcode;
        }

        public void setRetcode(int retcode) {
            this.retcode = retcode;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getSubcode() {
            return subcode;
        }

        public void setSubcode(int subcode) {
            this.subcode = subcode;
        }

        public String getLyric() {
            return lyric;
        }

        public void setLyric(String lyric) {
            this.lyric = lyric;
        }

        public String getTrans() {
            return trans;
        }

        public void setTrans(String trans) {
            this.trans = trans;
        }
    }

    private class FileSize {

        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public class DataBean {


            private FileBean file;

            public FileBean getFile() {
                return file;
            }

            public void setFile(FileBean file) {
                this.file = file;
            }

            public class FileBean {


                private String media_mid;
                private int size_128mp3;
                private int size_192aac;
                private int size_192ogg;
                private int size_24aac;
                private int size_320mp3;
                private int size_48aac;
                private int size_96aac;
                private int size_ape;
                private int size_dts;
                private int size_flac;
                private int size_try;
                private int try_begin;
                private int try_end;

                public String getMedia_mid() {
                    return media_mid;
                }

                public void setMedia_mid(String media_mid) {
                    this.media_mid = media_mid;
                }

                public int getSize_128mp3() {
                    return size_128mp3;
                }

                public void setSize_128mp3(int size_128mp3) {
                    this.size_128mp3 = size_128mp3;
                }

                public int getSize_192aac() {
                    return size_192aac;
                }

                public void setSize_192aac(int size_192aac) {
                    this.size_192aac = size_192aac;
                }

                public int getSize_192ogg() {
                    return size_192ogg;
                }

                public void setSize_192ogg(int size_192ogg) {
                    this.size_192ogg = size_192ogg;
                }

                public int getSize_24aac() {
                    return size_24aac;
                }

                public void setSize_24aac(int size_24aac) {
                    this.size_24aac = size_24aac;
                }

                public int getSize_320mp3() {
                    return size_320mp3;
                }

                public void setSize_320mp3(int size_320mp3) {
                    this.size_320mp3 = size_320mp3;
                }

                public int getSize_48aac() {
                    return size_48aac;
                }

                public void setSize_48aac(int size_48aac) {
                    this.size_48aac = size_48aac;
                }

                public int getSize_96aac() {
                    return size_96aac;
                }

                public void setSize_96aac(int size_96aac) {
                    this.size_96aac = size_96aac;
                }

                public int getSize_ape() {
                    return size_ape;
                }

                public void setSize_ape(int size_ape) {
                    this.size_ape = size_ape;
                }

                public int getSize_dts() {
                    return size_dts;
                }

                public void setSize_dts(int size_dts) {
                    this.size_dts = size_dts;
                }

                public int getSize_flac() {
                    return size_flac;
                }

                public void setSize_flac(int size_flac) {
                    this.size_flac = size_flac;
                }

                public int getSize_try() {
                    return size_try;
                }

                public void setSize_try(int size_try) {
                    this.size_try = size_try;
                }

                public int getTry_begin() {
                    return try_begin;
                }

                public void setTry_begin(int try_begin) {
                    this.try_begin = try_begin;
                }

                public int getTry_end() {
                    return try_end;
                }

                public void setTry_end(int try_end) {
                    this.try_end = try_end;
                }
            }
        }
    }
}