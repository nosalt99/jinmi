package com.cyl.musiclake.player;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.WindowManager;

import com.cyl.musiclake.MusicApp;
import com.cyl.musiclake.R;
import com.cyl.musiclake.api.music.MusicApi;
import com.cyl.musiclake.api.music.MusicApiServiceImpl;
import com.cyl.musiclake.bean.Music;
import com.cyl.musiclake.api.net.ApiManager;
import com.cyl.musiclake.api.net.RequestCallBack;
import com.cyl.musiclake.ui.widget.LyricView;
import com.cyl.musiclake.ui.widget.lyric.FloatLyricView;
import com.cyl.musiclake.ui.widget.lyric.LyricInfo;
import com.cyl.musiclake.ui.widget.lyric.LyricParseUtils;
import com.cyl.musiclake.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class FloatLyricViewManager {
    private static final String TAG = "FloatLyricViewManager";
    private static FloatLyricView mFloatLyricView;
    private static WindowManager.LayoutParams mFloatLyricViewParams;
    private static WindowManager mWindowManager;
    private static LyricInfo mLyricInfo;
    private boolean mIsLock;
    private Handler handler = new Handler();
    private String mSongName;
    private static boolean isFirstSettingLyric; //第一次设置歌词


    public static String lyricInfo;


    private Context mContext;

    FloatLyricViewManager(Context context) {
        mContext = context;
    }


    private static List<LyricView> lyricViews = new ArrayList<>();

    public static void setLyricChangeListener(LyricView lyricView) {
        lyricViews.add(lyricView);
    }

    public static void removeLyricChangeListener(LyricView lyricView) {
        lyricViews.remove(lyricView);
    }



    public void updatePlayStatus(boolean isPlaying) {
        if (mFloatLyricView != null)
            mFloatLyricView.setPlayStatus(isPlaying);
    }



    public void loadLyric(Music mPlayingMusic) {
        resetLyric(MusicApp.getAppContext().getString(R.string.lyric_loading));
        if (mPlayingMusic != null) {
            mSongName = mPlayingMusic.getTitle();
            Observable<String> observable = MusicApi.INSTANCE.getLyricInfo(mPlayingMusic);
            if (observable != null) {
                ApiManager.request(observable, new RequestCallBack<String>() {
                    @Override
                    public void success(String result) {
                        updateLyric(result);
                    }

                    @Override
                    public void error(String msg) {
                        updateLyric("");
                        LogUtil.e("LoadLyric", msg);
                    }
                });
            } else {
                updateLyric("");
            }
        } else {
            updateLyric("");
        }
    }


    public static void saveLyricInfo(String name, String artist, String info) {
        lyricInfo = info;
        MusicApiServiceImpl.INSTANCE.saveLyricInfo(name, artist, info);
        setLyric(lyricInfo);
        for (int i = 0; i < lyricViews.size(); i++) {
            lyricViews.get(i).setLyricContent(info);
        }
    }


    private void resetLyric(String info) {
        lyricInfo = info;
        setLyric(lyricInfo);
        for (int i = 0; i < lyricViews.size(); i++) {
            lyricViews.get(i).reset(info);
        }
    }


    private void updateLyric(String info) {
        lyricInfo = info;
        setLyric(lyricInfo);
        for (int i = 0; i < lyricViews.size(); i++) {
            lyricViews.get(i).setLyricContent(info);
        }
    }



    public static void setLyric(String lyricInfo) {
        mLyricInfo = LyricParseUtils.setLyricResource(lyricInfo);
        isFirstSettingLyric = true;
    }



    private boolean isHome() {
        try {
            return MusicApp.count != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    private void createFloatLyricView(Context context) {
        try {
            WindowManager windowManager = getWindowManager();
            Point size = new Point();
            //获取屏幕宽高
            windowManager.getDefaultDisplay().getSize(size);
            int screenWidth = size.x;
            int screenHeight = size.y;
            if (mFloatLyricView == null) {
                mFloatLyricView = new FloatLyricView(context);
                if (mFloatLyricViewParams == null) {
                    mFloatLyricViewParams = new WindowManager.LayoutParams();
                    mFloatLyricViewParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        mFloatLyricViewParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                    } else {
                        mFloatLyricViewParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                    }

                    mFloatLyricViewParams.format = PixelFormat.RGBA_8888;
                    mFloatLyricViewParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                    mFloatLyricViewParams.gravity = Gravity.START | Gravity.TOP;
                    mFloatLyricViewParams.width = mFloatLyricView.getViewWidth();
                    mFloatLyricViewParams.height = mFloatLyricView.getViewHeight();
                    mFloatLyricViewParams.x = screenWidth;
                    mFloatLyricViewParams.y = screenHeight / 2;
                }
                mFloatLyricView.setParams(mFloatLyricViewParams);
                windowManager.addView(mFloatLyricView, mFloatLyricViewParams);
                setLyric(lyricInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void removeFloatLyricView(Context context) {
        try {
            if (mFloatLyricView != null) {
                WindowManager windowManager = getWindowManager();
                windowManager.removeView(mFloatLyricView);
                mFloatLyricView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateLyric(long positon, long duration) {
        // 当前界面不是本应用界面，且没有悬浮窗显示，则创建悬浮窗。
        if (!isHome() && !isWindowShowing()) {
            handler.post(() -> createFloatLyricView(mContext));
        } else if (isHome() && isWindowShowing()) {
            handler.post(() -> removeFloatLyricView(mContext));
        } else if (isWindowShowing()) {
            handler.post(() -> {
                if (mFloatLyricView != null) {
                    if (isFirstSettingLyric) {
                        mFloatLyricView.getMTitle().setText(mSongName);
                        mFloatLyricView.getMLyricText().setLyricInfo(mLyricInfo);
                        isFirstSettingLyric = false;
                    }
                    mFloatLyricView.getMLyricText().setCurrentTimeMillis(positon);
                    mFloatLyricView.getMLyricText().setDurationMillis(duration);
                }
            });
        }

    }



    private static boolean isWindowShowing() {
        return mFloatLyricView != null;
    }


    private static WindowManager getWindowManager() {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) MusicApp.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }


    public void saveLock(boolean lock, boolean toast) {
        mFloatLyricView.saveLock(lock, toast);
    }

}
