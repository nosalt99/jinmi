package com.cyl.musiclake.api.net;


public interface RequestCallBack<T> {
    void success(T result);

    void error(String msg);
}
