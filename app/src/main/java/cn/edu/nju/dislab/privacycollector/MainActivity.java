package cn.edu.nju.dislab.privacycollector;

import android.app.Activity;
import android.hardware.Sensor;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startForegroundAppCollector();
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

    private void startSensorsCollector() {
        int[] typeSensors = new int[]{Sensor.TYPE_GYROSCOPE, Sensor.TYPE_MAGNETIC_FIELD, Sensor.TYPE_LIGHT};
        long[] maxTimes = new long[]{100000000, 100000000, 100000000};
        final SensorsCollector sensorsCollector = new SensorsCollector(typeSensors, maxTimes);
        new Thread(new Runnable() {
            @Override
            public void run() {
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
        }).start();
    }

    private void startScreenCollector() {
        final ScreenCollector screenCollector = new ScreenCollector();
        new Thread(new Runnable() {
            @Override
            public void run() {
                screenCollector.collect();
                boolean result = screenCollector.getResult();
                Log.i(TAG, "is screen on: " + result);
            }
        }).start();
    }

    private void startForegroundAppCollector() {
        final ForegroundAppCollector foregroundAppCollector = new ForegroundAppCollector();
        new Thread(new Runnable() {
            @Override
            public void run() {
                foregroundAppCollector.collect();
                String result = foregroundAppCollector.getResult();
                Log.i(TAG, "foreground app: " + result);
            }
        }).start();
    }
}
