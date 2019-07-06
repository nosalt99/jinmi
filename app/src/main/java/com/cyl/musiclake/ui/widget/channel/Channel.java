package com.cyl.musiclake.ui.widget.channel;


public class Channel {
    String channelName;
    int channelBelong;
    Object obj;
    int code = -1;

    public Channel(String channelName) {
        this(channelName, 1, null);
    }


    public Channel(String channelName, int channelBelong, Object obj) {
        this.channelName = channelName;
        this.channelBelong = channelBelong;
        this.obj = obj;
    }


    public Channel(String channelName, int channelBelong) {
        this(channelName, channelBelong, null);
    }


    public Channel(String channelName, Object obj) {
        this(channelName, 1, obj);
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelName='" + channelName + '\'' +
                ", obj=" + obj +
                '}';
    }
}
