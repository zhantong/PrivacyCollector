package cn.edu.nju.dislab.privacycollector;

import android.app.Activity;
import android.hardware.Sensor;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                startCollect();
            }
        }).start();
    }

    public void startCollect() {
        Thread wifiCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                WifiCollector wifiCollector = new WifiCollector();
                wifiCollector.collect();
                List<ScanResult> scanResults = wifiCollector.getResult();
                Log.i(TAG, scanResults.toString());
            }
        });
        Thread contactCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ContactCollector contactCollector = new ContactCollector();
                contactCollector.collect();
                List<String[]> results = contactCollector.getResult();
                if (results != null) {
                    for (String[] item : results) {
                        Log.i(TAG, "name: " + item[0] + " number: " + item[1]);
                    }
                }
            }
        });
        Thread callLogCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                CallLogCollector callLogCollector = new CallLogCollector();
                callLogCollector.collect();
                List<String[]> results = callLogCollector.getResult();
                if (results != null) {
                    for (String[] item : results) {
                        Log.i(TAG, "number: " + item[0] + " type: " + item[1] + " date: " + item[2] + " duration: " + item[3]);
                    }
                }
            }
        });
        Thread audioCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                AudioCollector audioCollector = new AudioCollector();
                audioCollector.collect();
                List<Double> results = audioCollector.getResult();
                if (results != null) {
                    Log.i(TAG, results.toString());
                }
            }
        });
        Thread locationCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                LocationCollector locationCollector = new LocationCollector();
                locationCollector.collect();
                AMapLocation result = locationCollector.getResult();
                if (result != null) {
                    Log.i(TAG, "location: " + result.getErrorCode() + " " + result.getErrorInfo() + "\n" + result.getAddress());
                }
            }
        });
        Thread smsCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                SmsCollector smsCollector = new SmsCollector();
                smsCollector.collect();
                List<String[]> results = smsCollector.getResult();
                if (results != null) {
                    for (String[] item : results) {
                        Log.i(TAG, "address: " + item[0] + " type: " + item[1] + " date: " + item[2] + " person: " + item[3]);
                    }
                }
            }
        });
        Thread sensorCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int[] typeSensors = new int[]{Sensor.TYPE_GYROSCOPE, Sensor.TYPE_MAGNETIC_FIELD, Sensor.TYPE_LIGHT, Sensor.TYPE_ACCELEROMETER};
                long[] maxTimes = new long[]{100000000, 100000000, 100000000, 100000000};
                final SensorsCollector sensorsCollector = new SensorsCollector(typeSensors, maxTimes);
                sensorsCollector.collect();
                Map<Integer, ArrayList<SensorData>> results = sensorsCollector.getResult();
                if (results != null) {
                    for (Map.Entry<Integer, ArrayList<SensorData>> entry : results.entrySet()) {
                        int typeSensor = entry.getKey();
                        ArrayList<SensorData> result = entry.getValue();
                        for (SensorData sensorData : result) {
                            Log.i(TAG, "sensor " + typeSensor + " " + sensorData.toString());
                        }
                    }
                }
            }
        });
        Thread screenCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ScreenCollector screenCollector = new ScreenCollector();
                screenCollector.collect();
                boolean result = screenCollector.getResult();
                Log.i(TAG, "is screen on: " + result);
            }
        });
        Thread foregroundAppCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ForegroundAppCollector foregroundAppCollector = new ForegroundAppCollector();
                foregroundAppCollector.collect();
                String result = foregroundAppCollector.getResult();
                Log.i(TAG, "foreground app: " + result);
            }
        });
        Thread runningAppCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                RunningAppCollector runningAppCollector = new RunningAppCollector();
                runningAppCollector.collect();
                List<String> results = runningAppCollector.getResult();
                if (results != null) {
                    Log.i(TAG, "running apps: " + results.toString());
                }
            }
        });

        Thread[] threads = new Thread[]{wifiCollectorThread, contactCollectorThread, callLogCollectorThread,
                audioCollectorThread, locationCollectorThread, smsCollectorThread, sensorCollectorThread,
                screenCollectorThread, foregroundAppCollectorThread, runningAppCollectorThread};
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "all done");
    }
}
