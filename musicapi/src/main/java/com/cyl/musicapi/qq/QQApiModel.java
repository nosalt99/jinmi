package com.cyl.musicapi.qq;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class QQApiModel {

    private int code;
    private DataBean data;
    private int time;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public static class DataBean {


        private String keyword;
        private SongBean song;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public SongBean getSong() {
            return song;
        }

        public void setSong(SongBean song) {
            this.song = song;
        }

        public static class SongBean {


            private int curnum;
            private int curpage;
            private int totalnum;
            private List<ListBean> list;

            public int getCurnum() {
                return curnum;
            }

            public void setCurnum(int curnum) {
                this.curnum = curnum;
            }

            public int getCurpage() {
                return curpage;
            }

            public void setCurpage(int curpage) {
                this.curpage = curpage;
            }

            public int getTotalnum() {
                return totalnum;
            }

            public void setTotalnum(int totalnum) {
                this.totalnum = totalnum;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                @Override
                public String toString() {
                    return "ListBean{" +
                            "albumid=" + albumid +
                            ", albummid='" + albummid + '\'' +
                            ", albumname='" + albumname + '\'' +
                            ", albumname_hilight='" + albumname_hilight + '\'' +
                            ", alertid=" + alertid +
                            ", chinesesinger=" + chinesesinger +
                            ", docid='" + docid + '\'' +
                            ", interval=" + interval +
                            ", isonly=" + isonly +
                            ", lyric='" + lyric + '\'' +
                            ", lyric_hilight='" + lyric_hilight + '\'' +
                            ", media_mid='" + media_mid + '\'' +
                            ", msgid=" + msgid +
                            ", pay=" + pay +
                            ", preview=" + preview +
                            ", pubtime=" + pubtime +
                            ", pure=" + pure +
                            ", size128=" + size128 +
                            ", size320=" + size320 +
                            ", sizeape=" + sizeape +
                            ", sizeflac=" + sizeflac +
                            ", sizeogg=" + sizeogg +
                            ", songid=" + songid +
                            ", songmid='" + songmid + '\'' +
                            ", songname='" + songname + '\'' +
                            ", songname_hilight='" + songname_hilight + '\'' +
                            ", strMediaMid='" + strMediaMid + '\'' +
                            ", stream=" + stream +
                            ", switchX=" + switchX +
                            ", t=" + t +
                            ", tag=" + tag +
                            ", type=" + type +
                            ", ver=" + ver +
                            ", vid='" + vid + '\'' +
                            ", grp=" + grp +
                            ", singer=" + singer +
                            '}';
                }



                private int albumid;
                private String albummid;
                private String albumname;
                private String albumname_hilight;
                private int alertid;
                private int chinesesinger;
                private String docid;
                private int interval;
                private int isonly;
                private String lyric;
                private String lyric_hilight;
                private String media_mid;
                private int msgid;
                private PayBean pay;
                private PreviewBean preview;
                private int pubtime;
                private int pure;
                private int size128;
                private int size320;
                private int sizeape;
                private int sizeflac;
                private int sizeogg;
                private int songid;
                private String songmid;
                private String songname;
                private String songname_hilight;
                private String strMediaMid;
                private int stream;
                @SerializedName("switch")
                private int switchX;
                private int t;
                private int tag;
                private int type;
                private int ver;
                private String vid;
                private List<GrpBean> grp;
                private List<SingerBeanX> singer;

                public int getAlbumid() {
                    return albumid;
                }

                public void setAlbumid(int albumid) {
                    this.albumid = albumid;
                }

                public String getAlbummid() {
                    return albummid;
                }

                public void setAlbummid(String albummid) {
                    this.albummid = albummid;
                }

                public String getAlbumname() {
                    return albumname;
                }

                public void setAlbumname(String albumname) {
                    this.albumname = albumname;
                }

                public String getAlbumname_hilight() {
                    return albumname_hilight;
                }

                public void setAlbumname_hilight(String albumname_hilight) {
                    this.albumname_hilight = albumname_hilight;
                }

                public int getAlertid() {
                    return alertid;
                }

                public void setAlertid(int alertid) {
                    this.alertid = alertid;
                }

                public int getChinesesinger() {
                    return chinesesinger;
                }

                public void setChinesesinger(int chinesesinger) {
                    this.chinesesinger = chinesesinger;
                }

                public String getDocid() {
                    return docid;
                }

                public void setDocid(String docid) {
                    this.docid = docid;
                }

                public int getInterval() {
                    return interval;
                }

                public void setInterval(int interval) {
                    this.interval = interval;
                }

                public int getIsonly() {
                    return isonly;
                }

                public void setIsonly(int isonly) {
                    this.isonly = isonly;
                }

                public String getLyric() {
                    return lyric;
                }

                public void setLyric(String lyric) {
                    this.lyric = lyric;
                }

                public String getLyric_hilight() {
                    return lyric_hilight;
                }

                public void setLyric_hilight(String lyric_hilight) {
                    this.lyric_hilight = lyric_hilight;
                }

                public String getMedia_mid() {
                    return media_mid;
                }

                public void setMedia_mid(String media_mid) {
                    this.media_mid = media_mid;
                }

                public int getMsgid() {
                    return msgid;
                }

                public void setMsgid(int msgid) {
                    this.msgid = msgid;
                }

                public PayBean getPay() {
                    return pay;
                }

                public void setPay(PayBean pay) {
                    this.pay = pay;
                }

                public PreviewBean getPreview() {
                    return preview;
                }

                public void setPreview(PreviewBean preview) {
                    this.preview = preview;
                }

                public int getPubtime() {
                    return pubtime;
                }

                public void setPubtime(int pubtime) {
                    this.pubtime = pubtime;
                }

                public int getPure() {
                    return pure;
                }

                public void setPure(int pure) {
                    this.pure = pure;
                }

                public int getSize128() {
                    return size128;
                }

                public void setSize128(int size128) {
                    this.size128 = size128;
                }

                public int getSize320() {
                    return size320;
                }

                public void setSize320(int size320) {
                    this.size320 = size320;
                }

                public int getSizeape() {
                    return sizeape;
                }

                public void setSizeape(int sizeape) {
                    this.sizeape = sizeape;
                }

                public int getSizeflac() {
                    return sizeflac;
                }

                public void setSizeflac(int sizeflac) {
                    this.sizeflac = sizeflac;
                }

                public int getSizeogg() {
                    return sizeogg;
                }

                public void setSizeogg(int sizeogg) {
                    this.sizeogg = sizeogg;
                }

                public int getSongid() {
                    return songid;
                }

                public void setSongid(int songid) {
                    this.songid = songid;
                }

                public String getSongmid() {
                    return songmid;
                }

                public void setSongmid(String songmid) {
                    this.songmid = songmid;
                }

                public String getSongname() {
                    return songname;
                }

                public void setSongname(String songname) {
                    this.songname = songname;
                }

                public String getSongname_hilight() {
                    return songname_hilight;
                }

                public void setSongname_hilight(String songname_hilight) {
                    this.songname_hilight = songname_hilight;
                }

                public String getStrMediaMid() {
                    return strMediaMid;
                }

                public void setStrMediaMid(String strMediaMid) {
                    this.strMediaMid = strMediaMid;
                }

                public int getStream() {
                    return stream;
                }

                public void setStream(int stream) {
                    this.stream = stream;
                }

                public int getSwitchX() {
                    return switchX;
                }

                public void setSwitchX(int switchX) {
                    this.switchX = switchX;
                }

                public int getT() {
                    return t;
                }

                public void setT(int t) {
                    this.t = t;
                }

                public int getTag() {
                    return tag;
                }

                public void setTag(int tag) {
                    this.tag = tag;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getVer() {
                    return ver;
                }

                public void setVer(int ver) {
                    this.ver = ver;
                }

                public String getVid() {
                    return vid;
                }

                public void setVid(String vid) {
                    this.vid = vid;
                }

                public List<GrpBean> getGrp() {
                    return grp;
                }

                public void setGrp(List<GrpBean> grp) {
                    this.grp = grp;
                }

                public List<SingerBeanX> getSinger() {
                    return singer;
                }

                public void setSinger(List<SingerBeanX> singer) {
                    this.singer = singer;
                }

                public static class PayBean {


                    private int payalbum;
                    private int payalbumprice;
                    private int paydownload;
                    private int payinfo;
                    private int payplay;
                    private int paytrackmouth;
                    private int paytrackprice;

                    public int getPayalbum() {
                        return payalbum;
                    }

                    public void setPayalbum(int payalbum) {
                        this.payalbum = payalbum;
                    }

                    public int getPayalbumprice() {
                        return payalbumprice;
                    }

                    public void setPayalbumprice(int payalbumprice) {
                        this.payalbumprice = payalbumprice;
                    }

                    public int getPaydownload() {
                        return paydownload;
                    }

                    public void setPaydownload(int paydownload) {
                        this.paydownload = paydownload;
                    }

                    public int getPayinfo() {
                        return payinfo;
                    }

                    public void setPayinfo(int payinfo) {
                        this.payinfo = payinfo;
                    }

                    public int getPayplay() {
                        return payplay;
                    }

                    public void setPayplay(int payplay) {
                        this.payplay = payplay;
                    }

                    public int getPaytrackmouth() {
                        return paytrackmouth;
                    }

                    public void setPaytrackmouth(int paytrackmouth) {
                        this.paytrackmouth = paytrackmouth;
                    }

                    public int getPaytrackprice() {
                        return paytrackprice;
                    }

                    public void setPaytrackprice(int paytrackprice) {
                        this.paytrackprice = paytrackprice;
                    }
                }

                public static class PreviewBean {


                    private int trybegin;
                    private int tryend;
                    private int trysize;

                    public int getTrybegin() {
                        return trybegin;
                    }

                    public void setTrybegin(int trybegin) {
                        this.trybegin = trybegin;
                    }

                    public int getTryend() {
                        return tryend;
                    }

                    public void setTryend(int tryend) {
                        this.tryend = tryend;
                    }

                    public int getTrysize() {
                        return trysize;
                    }

                    public void setTrysize(int trysize) {
                        this.trysize = trysize;
                    }
                }

                public static class GrpBean {


                    private int albumid;
                    private String albummid;
                    private String albumname;
                    private String albumname_hilight;
                    private int alertid;
                    private int chinesesinger;
                    private String docid;
                    private int interval;
                    private int isonly;
                    private String lyric;
                    private String lyric_hilight;
                    private String media_mid;
                    private int msgid;
                    private int nt;
                    private PayBeanX pay;
                    private PreviewBeanX preview;
                    private int pubtime;
                    private int pure;
                    private int size128;
                    private int size320;
                    private int sizeape;
                    private int sizeflac;
                    private int sizeogg;
                    private int songid;
                    private String songmid;
                    private String songname;
                    private String songname_hilight;
                    private String strMediaMid;
                    private int stream;
                    @SerializedName("switch")
                    private int switchX;
                    private int t;
                    private int tag;
                    private int type;
                    private int ver;
                    private String vid;
                    private List<SingerBean> singer;

                    public int getAlbumid() {
                        return albumid;
                    }

                    public void setAlbumid(int albumid) {
                        this.albumid = albumid;
                    }

                    public String getAlbummid() {
                        return albummid;
                    }

                    public void setAlbummid(String albummid) {
                        this.albummid = albummid;
                    }

                    public String getAlbumname() {
                        return albumname;
                    }

                    public void setAlbumname(String albumname) {
                        this.albumname = albumname;
                    }

                    public String getAlbumname_hilight() {
                        return albumname_hilight;
                    }

                    public void setAlbumname_hilight(String albumname_hilight) {
                        this.albumname_hilight = albumname_hilight;
                    }

                    public int getAlertid() {
                        return alertid;
                    }

                    public void setAlertid(int alertid) {
                        this.alertid = alertid;
                    }

                    public int getChinesesinger() {
                        return chinesesinger;
                    }

                    public void setChinesesinger(int chinesesinger) {
                        this.chinesesinger = chinesesinger;
                    }

                    public String getDocid() {
                        return docid;
                    }

                    public void setDocid(String docid) {
                        this.docid = docid;
                    }

                    public int getInterval() {
                        return interval;
                    }

                    public void setInterval(int interval) {
                        this.interval = interval;
                    }

                    public int getIsonly() {
                        return isonly;
                    }

                    public void setIsonly(int isonly) {
                        this.isonly = isonly;
                    }

                    public String getLyric() {
                        return lyric;
                    }

                    public void setLyric(String lyric) {
                        this.lyric = lyric;
                    }

                    public String getLyric_hilight() {
                        return lyric_hilight;
                    }

                    public void setLyric_hilight(String lyric_hilight) {
                        this.lyric_hilight = lyric_hilight;
                    }

                    public String getMedia_mid() {
                        return media_mid;
                    }

                    public void setMedia_mid(String media_mid) {
                        this.media_mid = media_mid;
                    }

                    public int getMsgid() {
                        return msgid;
                    }

                    public void setMsgid(int msgid) {
                        this.msgid = msgid;
                    }

                    public int getNt() {
                        return nt;
                    }

                    public void setNt(int nt) {
                        this.nt = nt;
                    }

                    public PayBeanX getPay() {
                        return pay;
                    }

                    public void setPay(PayBeanX pay) {
                        this.pay = pay;
                    }

                    public PreviewBeanX getPreview() {
                        return preview;
                    }

                    public void setPreview(PreviewBeanX preview) {
                        this.preview = preview;
                    }

                    public int getPubtime() {
                        return pubtime;
                    }

                    public void setPubtime(int pubtime) {
                        this.pubtime = pubtime;
                    }

                    public int getPure() {
                        return pure;
                    }

                    public void setPure(int pure) {
                        this.pure = pure;
                    }

                    public int getSize128() {
                        return size128;
                    }

                    public void setSize128(int size128) {
                        this.size128 = size128;
                    }

                    public int getSize320() {
                        return size320;
                    }

                    public void setSize320(int size320) {
                        this.size320 = size320;
                    }

                    public int getSizeape() {
                        return sizeape;
                    }

                    public void setSizeape(int sizeape) {
                        this.sizeape = sizeape;
                    }

                    public int getSizeflac() {
                        return sizeflac;
                    }

                    public void setSizeflac(int sizeflac) {
                        this.sizeflac = sizeflac;
                    }

                    public int getSizeogg() {
                        return sizeogg;
                    }

                    public void setSizeogg(int sizeogg) {
                        this.sizeogg = sizeogg;
                    }

                    public int getSongid() {
                        return songid;
                    }

                    public void setSongid(int songid) {
                        this.songid = songid;
                    }

                    public String getSongmid() {
                        return songmid;
                    }

                    public void setSongmid(String songmid) {
                        this.songmid = songmid;
                    }

                    public String getSongname() {
                        return songname;
                    }

                    public void setSongname(String songname) {
                        this.songname = songname;
                    }

                    public String getSongname_hilight() {
                        return songname_hilight;
                    }

                    public void setSongname_hilight(String songname_hilight) {
                        this.songname_hilight = songname_hilight;
                    }

                    public String getStrMediaMid() {
                        return strMediaMid;
                    }

                    public void setStrMediaMid(String strMediaMid) {
                        this.strMediaMid = strMediaMid;
                    }

                    public int getStream() {
                        return stream;
                    }

                    public void setStream(int stream) {
                        this.stream = stream;
                    }

                    public int getSwitchX() {
                        return switchX;
                    }

                    public void setSwitchX(int switchX) {
                        this.switchX = switchX;
                    }

                    public int getT() {
                        return t;
                    }

                    public void setT(int t) {
                        this.t = t;
                    }

                    public int getTag() {
                        return tag;
                    }

                    public void setTag(int tag) {
                        this.tag = tag;
                    }

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }

                    public int getVer() {
                        return ver;
                    }

                    public void setVer(int ver) {
                        this.ver = ver;
                    }

                    public String getVid() {
                        return vid;
                    }

                    public void setVid(String vid) {
                        this.vid = vid;
                    }

                    public List<SingerBean> getSinger() {
                        return singer;
                    }

                    public void setSinger(List<SingerBean> singer) {
                        this.singer = singer;
                    }

                    public static class PayBeanX {


                        private int payalbum;
                        private int payalbumprice;
                        private int paydownload;
                        private int payinfo;
                        private int payplay;
                        private int paytrackmouth;
                        private int paytrackprice;

                        public int getPayalbum() {
                            return payalbum;
                        }

                        public void setPayalbum(int payalbum) {
                            this.payalbum = payalbum;
                        }

                        public int getPayalbumprice() {
                            return payalbumprice;
                        }

                        public void setPayalbumprice(int payalbumprice) {
                            this.payalbumprice = payalbumprice;
                        }

                        public int getPaydownload() {
                            return paydownload;
                        }

                        public void setPaydownload(int paydownload) {
                            this.paydownload = paydownload;
                        }

                        public int getPayinfo() {
                            return payinfo;
                        }

                        public void setPayinfo(int payinfo) {
                            this.payinfo = payinfo;
                        }

                        public int getPayplay() {
                            return payplay;
                        }

                        public void setPayplay(int payplay) {
                            this.payplay = payplay;
                        }

                        public int getPaytrackmouth() {
                            return paytrackmouth;
                        }

                        public void setPaytrackmouth(int paytrackmouth) {
                            this.paytrackmouth = paytrackmouth;
                        }

                        public int getPaytrackprice() {
                            return paytrackprice;
                        }

                        public void setPaytrackprice(int paytrackprice) {
                            this.paytrackprice = paytrackprice;
                        }
                    }

                    public static class PreviewBeanX {


                        private int trybegin;
                        private int tryend;
                        private int trysize;

                        public int getTrybegin() {
                            return trybegin;
                        }

                        public void setTrybegin(int trybegin) {
                            this.trybegin = trybegin;
                        }

                        public int getTryend() {
                            return tryend;
                        }

                        public void setTryend(int tryend) {
                            this.tryend = tryend;
                        }

                        public int getTrysize() {
                            return trysize;
                        }

                        public void setTrysize(int trysize) {
                            this.trysize = trysize;
                        }
                    }

                    public static class SingerBean {


                        private int id;
                        private String mid;
                        private String name;
                        private String name_hilight;

                        public int getId() {
                            return id;
                        }

                        public void setId(int id) {
                            this.id = id;
                        }

                        public String getMid() {
                            return mid;
                        }

                        public void setMid(String mid) {
                            this.mid = mid;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public String getName_hilight() {
                            return name_hilight;
                        }

                        public void setName_hilight(String name_hilight) {
                            this.name_hilight = name_hilight;
                        }
                    }
                }

                public static class SingerBeanX {
                    @Override
                    public String toString() {
                        return "SingerBeanX{" +
                                "id=" + id +
                                ", mid='" + mid + '\'' +
                                ", name='" + name + '\'' +
                                ", name_hilight='" + name_hilight + '\'' +
                                '}';
                    }



                    private int id;
                    private String mid;
                    private String name;
                    private String name_hilight;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getMid() {
                        return mid;
                    }

                    public void setMid(String mid) {
                        this.mid = mid;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getName_hilight() {
                        return name_hilight;
                    }

                    public void setName_hilight(String name_hilight) {
                        this.name_hilight = name_hilight;
                    }
                }
            }
        }
    }
}
