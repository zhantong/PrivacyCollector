package cn.edu.nju.dislab.privacycollector;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
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
        DbHelper dbHelper = new DbHelper();
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Thread wifiCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                WifiCollector wifiCollector = new WifiCollector();
                wifiCollector.collect();
                List<ScanResult> results = wifiCollector.getResult();
                CollectorToDb.writeWifi(db, results);
                Log.i(TAG, results.toString());
            }
        });
        Thread contactCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ContactCollector contactCollector = new ContactCollector();
                contactCollector.collect();
                List<ContactData> results = contactCollector.getResult();
                CollectorToDb.writeContact(db, results);
                if (results != null) {
                    Log.i(TAG, results.toString());
                }
            }
        });
        Thread callLogCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                CallLogCollector callLogCollector = new CallLogCollector();
                callLogCollector.collect();
                List<CallLogData> results = callLogCollector.getResult();
                CollectorToDb.writeCallLog(db, results);
                if (results != null) {
                    Log.i(TAG, results.toString());
                }
            }
        });
        Thread audioCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                AudioCollector audioCollector = new AudioCollector();
                audioCollector.collect();
                List<AudioData> results = audioCollector.getResult();
                CollectorToDb.writeAudio(db, results);
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
                CollectorToDb.writeLocation(db, result);
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
                List<SmsData> results = smsCollector.getResult();
                CollectorToDb.writeSms(db, results);
                if (results != null) {
                    Log.i(TAG, results.toString());
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
                CollectorToDb.writeSensors(db, results);
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
                ScreenData result = screenCollector.getResult();
                CollectorToDb.writeScreen(db, result);
                Log.i(TAG, result.toString());
            }
        });
        Thread foregroundAppCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ForegroundAppCollector foregroundAppCollector = new ForegroundAppCollector();
                foregroundAppCollector.collect();
                ForegroundAppData result = foregroundAppCollector.getResult();
                CollectorToDb.writeForegroundApp(db, result);
                Log.i(TAG, "foreground app: " + result.toString());
            }
        });
        Thread runningAppCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                RunningAppCollector runningAppCollector = new RunningAppCollector();
                runningAppCollector.collect();
                List<RunningAppData> results = runningAppCollector.getResult();
                CollectorToDb.writeRunningApp(db, results);
                if (results != null) {
                    Log.i(TAG, results.toString());
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
