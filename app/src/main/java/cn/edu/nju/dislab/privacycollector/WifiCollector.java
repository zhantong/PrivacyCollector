package cn.edu.nju.dislab.privacycollector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

/**
 * Created by zhantong on 2016/12/21.
 */

public class WifiCollector {
    private static final String TAG = "WifiCollector";
    private WifiManager mWifiManager;
    private Context mContext;
    private List<ScanResult> scanResults;

    public WifiCollector() {
        this(MainApplication.getContext());
    }

    public WifiCollector(Context context) {
        mContext = context;
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        if (mWifiManager == null) {
            Log.i(TAG, "null WifiManager");
            throw new RuntimeException();
        }
    }

    public void collect() {
        boolean originIsWifiEnabled = mWifiManager.isWifiEnabled();
        if (!originIsWifiEnabled) {
            boolean isEnableWifiSuccess = false;
            try {
                isEnableWifiSuccess = mWifiManager.setWifiEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!isEnableWifiSuccess) {
                Log.i(TAG, "failed to enable wifi");
                return;
            }
        }
        mWifiManager.startScan();
        Log.i(TAG, "scan started");
        final Object scanFinished = new Object();
        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                synchronized (scanFinished) {
                    scanFinished.notify();
                }
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        synchronized (scanFinished) {
            try {
                scanFinished.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            scanResults = mWifiManager.getScanResults();
        }
    }

    public List<ScanResult> getResult() {
        return scanResults;
    }
}
