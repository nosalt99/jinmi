package com.cyl.musiclake.utils;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;

import com.cyl.musiclake.MusicApp;
import com.cyl.musiclake.utils.rom.FloatUtil;

import java.util.List;


public class SystemUtils {
    //判断是否是android 6.0
    public static boolean isJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    //判断是否是android 8.0
    public static boolean isO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }


    //判断是否是android 6.0
    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    //判断是否是android 5.0
    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    //判断是否是android 4.0
    public static boolean isKITKAT() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }



    public static boolean isOpenFloatWindow() {
        return FloatUtil.INSTANCE.checkPermission(MusicApp.getAppContext());
    }


    public static void applySystemWindow() {
        FloatUtil.INSTANCE.applyOrShowFloatWindow(MusicApp.getAppContext());
    }



    public static boolean isOpenUsageAccess() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && isNoOptions()) {
            return isNoSwitch();
        } else {
            return true;
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static boolean isNoOptions() {
        PackageManager packageManager = MusicApp.getAppContext().getPackageManager();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static boolean isNoSwitch() {
        long dujinyang = System.currentTimeMillis();
        UsageStatsManager usageStatsManager = (UsageStatsManager) MusicApp.getAppContext().getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> queryUsageStats = null;
        if (usageStatsManager != null) {
            queryUsageStats = usageStatsManager.queryUsageStats(
                    UsageStatsManager.INTERVAL_BEST, 0, dujinyang);
        }
        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return false;
        }
        return true;
    }

}
