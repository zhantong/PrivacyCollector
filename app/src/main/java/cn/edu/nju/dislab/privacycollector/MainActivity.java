package cn.edu.nju.dislab.privacycollector;

import android.app.Activity;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocation;

import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startSmsCollector();
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

    private void startCallLogCollector() {
        final CallLogCollector callLogCollector = new CallLogCollector();
        new Thread(new Runnable() {
            @Override
            public void run() {
                callLogCollector.collect();
                List<String[]> results = callLogCollector.getResult();
                if (results != null) {
                    for (String[] item : results) {
                        Log.i(TAG, "number: " + item[0] + " type: " + item[1] + " date: " + item[2] + " duration: " + item[3]);
                    }
                }
            }
        }).start();
    }

    private void startAudioCollector() {
        final AudioCollector audioCollector = new AudioCollector();
        new Thread(new Runnable() {
            @Override
            public void run() {
                audioCollector.collect();
                List<Double> results = audioCollector.getResult();
                if (results != null) {
                    Log.i(TAG, results.toString());
                }
            }
        }).start();
    }

    private void startGpsCollector() {
        final LocationCollector locationCollector = new LocationCollector();
        new Thread(new Runnable() {
            @Override
            public void run() {
                locationCollector.collect();
                BDLocation result = locationCollector.getResult();
                if (result != null) {
                    Log.i(TAG, "loc type: " + result.getLocType() + " （161： 网络定位结果，网络定位成功。）");
                }
            }
        }).start();
    }

    private void startSmsCollector() {
        final SmsColletor smsColletor = new SmsColletor();
        new Thread(new Runnable() {
            @Override
            public void run() {
                smsColletor.collect();
                List<String[]> results = smsColletor.getResult();
                if (results != null) {
                    for (String[] item : results) {
                        Log.i(TAG, "address: " + item[0] + " type: " + item[1] + " date: " + item[2] + " person: " + item[3]);
                    }
                }
            }
        }).start();
    }
}
