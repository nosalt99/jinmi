package com.cyl.musiclake.ui.homepage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyl.musiclake.R;
import com.cyl.musiclake.ui.base.BaseActivity;
import com.cyl.musiclake.ui.focus.FocusActivity;
import com.cyl.musiclake.ui.main.MainActivity;
import com.cyl.musiclake.ui.main.WelcomeActivity;
import com.cyl.musiclake.ui.personal.PersonalActivity;
import com.cyl.musiclake.ui.sleep.SleepActivity;
import com.cyl.musiclake.utils.SPUtils;
import com.cyl.musiclake.utils.SystemUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.ivMusic)
    ImageView ivMusic;
    @BindView(R.id.ivPersonal)
    ImageView ivPersonal;
    @BindView(R.id.ivFocus)
    ImageView ivFocus;
    @BindView(R.id.ivSleep)
    ImageView ivSleep;
    @BindView(R.id.tvMorning)
    TextView tvMorning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    RxPermissions rxPermissions;

    //需要检查的权限
    private final String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            //获取电话状态
            Manifest.permission.READ_PHONE_STATE,
    };

    @Override
    protected void listener() {

        ivMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });
        ivPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });
        ivFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FocusActivity.class);
                startActivity(intent);
            }
        });
        ivSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SleepActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_homepage;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        rxPermissions = new RxPermissions(this);
        if (SystemUtils.isMarshmallow()) {
            checkPermissionAndThenLoad();
        } else {
            initWelcome();
        }
    }

    @Override
    protected void initInjector() {

    }


    @SuppressLint("CheckResult")
    private void checkPermissionAndThenLoad() {
        rxPermissions.request(mPermissionList)
                .subscribe(granted -> {
                    if (granted) {
                        initWelcome();
                    } else {
//                        Snackbar.make(container, getResources().getString(R.string.permission_hint),
//                                Snackbar.LENGTH_INDEFINITE)
//                                .setAction(getResources().getString(R.string.sure), view -> checkPermissionAndThenLoad()).show();
                    }
                });
    }


    private void initWelcome() {
        boolean isFirst = SPUtils.getAnyByKey(SPUtils.SP_KEY_FIRST_COMING, true);
        if (isFirst) {
            getCoverImageUrl();
            SPUtils.putAnyCommit(SPUtils.SP_KEY_FIRST_COMING, false);
        } else {
            //mHandler.postDelayed(WelcomeActivity.this::startMainActivity, 1000);
        }
    }


    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
//        overridePendingTransition(0, 0);
    }

    private void getCoverImageUrl() {
        //mHandler.postDelayed(WelcomeActivity.this::startMainActivity, 3000);
    }
}
