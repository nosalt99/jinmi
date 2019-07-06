package com.cyl.musiclake.ui.focus;

import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyl.musiclake.R;
import com.cyl.musiclake.ui.base.BaseFragment;
import com.cyl.musiclake.ui.base.BasePresenter;
import com.cyl.musiclake.ui.main.PageAdapter;
import com.cyl.musiclake.ui.music.local.adapter.MyViewPagerAdapter;
import com.cyl.musiclake.ui.sleep.SleepActivity;
import com.cyl.musiclake.utils.DisplayUtils;
import com.erz.timepicker_library.TimePicker;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class FocusEnvFragment extends BaseFragment<BasePresenter> {

    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.cc_bg)
    ConstraintLayout ccBg;

    private String env = "雨天";
    private int time;
    private boolean playing = false;
    private int currentTime;
    private CountDownTimer countDownTimer;

    public static Map<String, Integer> bgMap = new HashMap<String, Integer>(){
        {
            put("雨天", R.mipmap.a1);
            put("海洋", R.mipmap.a2);
            put("冥想", R.mipmap.a3);
            put("风暴", R.mipmap.a4);
        }
    };

    public static Map<String, Integer> timeMap = new HashMap<String, Integer>(){
        {
            put("雨天", 190);
            put("海洋", 201);
            put("冥想", 208);
            put("风暴", 201);
        }
    };

    public static FocusEnvFragment newInstance(String env, int time) {
        Bundle args = new Bundle();
        args.putCharSequence("env",env);
        args.putInt("time", timeMap.get(env));
        FocusEnvFragment fragment = new FocusEnvFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_focus;
    }

    @Override
    public void initViews() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            env = bundle.getString("env");
            time = bundle.getInt("time");
        }
        currentTime = time;
        mTvType.setText(env);
        mTvTime.setText(getTime(time));
        ccBg.setBackgroundResource(bgMap.get(env));

        //initMediaPlayer();
    }

//    private void initMediaPlayer() {
//        try {
//            AssetFileDescriptor fd = getActivity().getAssets().openFd(musicMap.get(env));
//            mediaPlayer.setDataSource(fd);
//            mediaPlayer.setLooping(true);//设置为循环播放
//            mediaPlayer.prepare();//初始化播放器MediaPlayer
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public void startCountDown() {
        countDownTimer = new CountDownTimer(time*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentTime -= 1;
                if (mTvTime != null) {
                    mTvTime.setText(getTime(currentTime));
                }
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
    }

    public void resetCountDown() {
        if (mTvTime != null) {
            currentTime = time;
            mTvTime.setText(getTime(time));
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    private String getTime(int time) {
        int minit = (int) (time/60);
        String minitTemp = minit+"";
        if (minit<10) minitTemp = "0"+minitTemp;
        int seconds = (int) (time % 60);
        String secondsTemp = seconds+"";
        if (seconds<10) secondsTemp = "0"+secondsTemp;
        return minitTemp+" : "+secondsTemp;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void listener() {

    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
