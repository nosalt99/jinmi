package com.cyl.musicapi.xiami;

import java.util.List;



public class XiamiModel {


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

    public static class DataBean {


        private int total;
        private List<SongsBean> songs;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<SongsBean> getSongs() {
            return songs;
        }

        public void setSongs(List<SongsBean> songs) {
            this.songs = songs;
        }

        public static class SongsBean {


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

            public static class PurviewRolesBean {


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

                public static class OperationListBean {


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
