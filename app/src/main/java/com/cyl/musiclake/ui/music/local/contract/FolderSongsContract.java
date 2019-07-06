package com.cyl.musiclake.ui.music.local.contract;

import com.cyl.musiclake.bean.Music;
import com.cyl.musiclake.ui.base.BaseContract;

import java.util.List;



public interface FolderSongsContract {

     interface View extends BaseContract.BaseView {

        void showEmptyView();

        void showSongs(List<Music> musicList);

    }

     interface Presenter extends BaseContract.BasePresenter<View> {

        void loadSongs(String path);

    }
}
