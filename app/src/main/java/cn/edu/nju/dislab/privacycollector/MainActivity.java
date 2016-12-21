package cn.edu.nju.dislab.privacycollector;

import android.app.Activity;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startContactCollector();
    }

    private void startWifiCollector() {
        final WifiCollector wifiCollector = new WifiCollector();
        new Thread(new Runnable() {
            @Override
            public void run() {
                wifiCollector.collect();
                List<ScanResult> scanResults = wifiCollector.getResult();
                Log.i(TAG, scanResults.toString());
            }
        }).start();
    }

    private void startContactCollector() {
        final ContactCollector contactCollector = new ContactCollector();
        new Thread(new Runnable() {
            @Override
            public void run() {
                contactCollector.collect();
                List<String[]> results = contactCollector.getResult();
                if (results != null) {
                    for (String[] item : results) {
                        Log.i(TAG, "name: " + item[0] + " number: " + item[1]);
                    }
                }
            }
        }).start();
    }
}
