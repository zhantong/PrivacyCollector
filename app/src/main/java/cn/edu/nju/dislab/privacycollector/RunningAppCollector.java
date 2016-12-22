package cn.edu.nju.dislab.privacycollector;

import android.Manifest;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhantong on 2016/12/22.
 */

public class RunningAppCollector {
    private static final String TAG = "RunningAppCollector";
    private static final String[] PERMISSIONS_LESS_L = {Manifest.permission.GET_TASKS};
    private static final String[] PERMISSIONS_EG_L = {Manifest.permission.PACKAGE_USAGE_STATS};
    private Context mContext;
    private ActivityManager mActivityManager;
    private UsageStatsManager mUsageStatsManager;
    private List<String> results;

    public RunningAppCollector() {
        this(MainApplication.getContext());
    }

    public RunningAppCollector(Context context) {
        mContext = context;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mUsageStatsManager = (UsageStatsManager) mContext.getSystemService(Context.USAGE_STATS_SERVICE);
            if (mUsageStatsManager == null) {
                Log.i(TAG, "null UsageStatsManager");
                throw new RuntimeException();
            }
            Log.i(TAG, "using UsageStatsManager");
        } else {
            mActivityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            if (mActivityManager == null) {
                Log.i(TAG, "null ActivityManager");
                throw new RuntimeException();
            }
            Log.i(TAG, "using ActivityManager");
        }
    }

    public void collect() {
        results = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            long INTERVAL = 30 * 60 * 1000;
            long currentTimeMillis = System.currentTimeMillis();
            List<UsageStats> usageStatses = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, currentTimeMillis - INTERVAL, currentTimeMillis + INTERVAL);
            for (UsageStats usageStats : usageStatses) {
                results.add(usageStats.getPackageName());
            }
        } else {
            List<ActivityManager.RunningTaskInfo> runningTaskInfos;
            try {
                runningTaskInfos = mActivityManager.getRunningTasks(Integer.MAX_VALUE);
            } catch (Exception e) {
                Log.i(TAG, "error when getting runningTaskInfos");
                e.printStackTrace();
                return;
            }
            if (runningTaskInfos == null) {
                Log.i(TAG, "null runningTaskInfos");
                return;
            }
            for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTaskInfos) {
                results.add(runningTaskInfo.baseActivity.getPackageName());
            }
        }
    }

    public List<String> getResult() {
        return results;
    }

    public static String[] getPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            return PERMISSIONS_EG_L;
        } else {
            return PERMISSIONS_LESS_L;
        }
    }
}
