package com.cyl.musiclake.ui.sleep;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

import com.cyl.musiclake.R;
import com.cyl.musiclake.ui.base.BaseFragment;
import com.cyl.musiclake.ui.base.BasePresenter;
import com.erz.timepicker_library.TimePicker;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class SleepEnvFragment extends BaseFragment<BasePresenter> {

    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.cc_bg)
    ConstraintLayout ccBg;
    @BindView(R.id.timePicker)
    TimePicker timePicker;

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

    public static SleepEnvFragment newInstance(String env) {
        Bundle args = new Bundle();
        args.putCharSequence("env",env);
        SleepEnvFragment fragment = new SleepEnvFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sleep;
    }

    @Override
    public void initViews() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            env = bundle.getString("env");
        }
        mTvType.setText(env);
        ccBg.setBackgroundResource(bgMap.get(env));
        //initMediaPlayer();
        timePicker.setTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void timeChanged(Date date) {
                SleepActivity.date = date;
            }
        });
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


    }

    public void resetCountDown() {

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
