package com.cyl.musiclake.ui.focus;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.cyl.musiclake.R;
import com.cyl.musiclake.common.NavigationHelper;
import com.cyl.musiclake.ui.base.BaseActivity;
import com.cyl.musiclake.ui.music.local.adapter.MyViewPagerAdapter;
import com.cyl.musiclake.utils.ToastUtils;
import com.erz.timepicker_library.TimePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class FocusActivity extends BaseActivity {

    @BindView(R.id.btn_focus)
    Button btnFocus;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.vp_focus)
    ViewPager vpFocus;
    @BindView(R.id.iv_music_effect)
    ImageView ivMusicEffect;


    List<FocusEnvFragment> fragmentList = new ArrayList<FocusEnvFragment>();
    public MediaPlayer mediaPlayer;
    private int currentPage;
    private int currentPlayPage = -1;

    private DevicePolicyManager policyManager;
    private ComponentName componentName;

    public static Map<Integer, String> musicMap = new HashMap<Integer, String>(){
        {
            put(-1, "rain.mp3");
            put(0, "rain.mp3");
            put(1, "sea.mp3");
            put(2, "think.mp3");
            put(3, "wind.mp3");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mediaPlayer = new MediaPlayer();
        policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, AdminReceiver.class);

        //判断是否有锁屏权限，若有则立即锁屏并结束自己，若没有则获取权限
        if (policyManager.isAdminActive(componentName)) {

        } else {
            activeManage();
        }

        super.onCreate(savedInstanceState);
        initMediaPlayer();
    }

    private void initMediaPlayer() {
        try {
            AssetFileDescriptor fd = getAssets().openFd("rain.mp3");
            mediaPlayer.setDataSource(fd);//指定音频文件路径
            mediaPlayer.setLooping(true);//设置为循环播放
            mediaPlayer.prepare();//初始化播放器MediaPlayer
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取权限，需要激活后再次点击才会锁屏
    private void activeManage() {
        //启动设备管理 - 在AndroidManifest.xml中设定相应过滤器
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        //权限列表
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        //描述
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "激活后才能使用锁屏功能哦");

        startActivity(intent);
    }

    @Override
    protected void listener() {
        super.listener();
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivMusicEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationHelper.INSTANCE.navigateToSoundEffect(FocusActivity.this);
            }
        });
        btnFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPlayPage == currentPage) {
                    mediaPlayer.reset();
                    fragmentList.get(currentPlayPage).resetCountDown();
                    currentPlayPage = -1;
                    btnFocus.setText("开始专注");
                } else {
                    vpFocus.setHorizontalScrollBarEnabled(false);
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.reset();
                    }
                    currentPlayPage = currentPage;
                    try {
                        AssetFileDescriptor fd = getAssets().openFd(musicMap.get(currentPage));
                        mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());//指定音频文件路径
                        mediaPlayer.setLooping(true);//设置为循环播放
                        mediaPlayer.prepare();//初始化播放器MediaPlayer
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < fragmentList.size(); i++) {
                        if (currentPlayPage == i) {
                            fragmentList.get(i).startCountDown();
                        } else {
                            fragmentList.get(i).resetCountDown();
                        }
                    }
                    mediaPlayer.start();
                    ToastUtils.show("闹钟设置完毕，5两秒后熄灭屏幕");
                    btnFocus.setText("停止专注");
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            policyManager.lockNow(); //立即锁屏
                        }
                    }, 5000);
                }

            }
        });
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_focus;
    }

    @Override
    protected void initView() {
        if (fragmentList.size() != 0) {
            fragmentList.clear();
        }
        fragmentList.add(FocusEnvFragment.newInstance("雨天", 1500));
        fragmentList.add(FocusEnvFragment.newInstance("海洋",1800));
        fragmentList.add(FocusEnvFragment.newInstance("冥想", 1400));
        fragmentList.add(FocusEnvFragment.newInstance("风暴",1200));
        vpFocus.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragmentList));
        vpFocus.setCurrentItem(0);
        //设置页面切换时的监听器(可选，用了之后要重写它的回调方法处理页面切换时候的事务)
        vpFocus.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPage = i;
                if (currentPlayPage == currentPage) {
                    btnFocus.setText("停止专注");
                } else {
                    btnFocus.setText("开始专注");
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void onDestroy() {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
