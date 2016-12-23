package cn.edu.nju.dislab.privacycollector;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startCollect();
            }
        });
        thread.start();
    }

    public void startCollect() {
        DbHelper dbHelper = new DbHelper();
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Thread wifiCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                WifiCollector wifiCollector = new WifiCollector();
                wifiCollector.collect();
                WifiData result = wifiCollector.getResult();
                if (result != null) {
                    result.toDb(db);
                    Log.i(TAG, result.toString());
                }
            }
        });
        Thread contactCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ContactCollector contactCollector = new ContactCollector();
                contactCollector.collect();
                ContactData result = contactCollector.getResult();
                if (result != null) {
                    result.toDb(db);
                    Log.i(TAG, result.toString());
                }
            }
        });
        Thread callLogCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                CallLogCollector callLogCollector = new CallLogCollector();
                callLogCollector.collect();
                CallLogData results = callLogCollector.getResult();
                if (results != null) {
                    results.toDb(db);
                    Log.i(TAG, results.toString());
                }
            }
        });
        Thread audioCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                AudioCollector audioCollector = new AudioCollector();
                audioCollector.collect();
                AudioData result = audioCollector.getResult();
                if (result != null) {
                    result.toDb(db);
                    Log.i(TAG, result.toString());
                }
            }
        });
        Thread locationCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                LocationCollector locationCollector = new LocationCollector();
                locationCollector.collect();
                LocationData result = locationCollector.getResult();
                if (result != null) {
                    result.toDb(db);
                    Log.i(TAG, result.toString());
                }
            }
        });
        Thread smsCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                SmsCollector smsCollector = new SmsCollector();
                smsCollector.collect();
                SmsData result = smsCollector.getResult();
                if (result != null) {
                    result.toDb(db);
                    Log.i(TAG, result.toString());
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
                SensorsData result = sensorsCollector.getResult();
                if (result != null) {
                    result.toDb(db);
                    Log.i(TAG, result.toString());
                }
            }
        });
        Thread screenCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ScreenCollector screenCollector = new ScreenCollector();
                screenCollector.collect();
                ScreenData result = screenCollector.getResult();
                if (result != null) {
                    result.toDb(db);
                    Log.i(TAG, result.toString());
                }
            }
        });
        Thread foregroundAppCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ForegroundAppCollector foregroundAppCollector = new ForegroundAppCollector();
                foregroundAppCollector.collect();
                ForegroundAppData result = foregroundAppCollector.getResult();
                if (result != null) {
                    result.toDb(db);
                    Log.i(TAG, result.toString());
                }
            }
        });
        Thread runningAppCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                RunningAppCollector runningAppCollector = new RunningAppCollector();
                runningAppCollector.collect();
                RunningAppData result = runningAppCollector.getResult();
                if (result != null) {
                    result.toDb(db);
                    Log.i(TAG, result.toString());
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
        db.close();
        dbHelper.close();
        Log.i(TAG, "all done");
    }
}
