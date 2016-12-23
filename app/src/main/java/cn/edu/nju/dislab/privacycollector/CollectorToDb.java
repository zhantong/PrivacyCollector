package cn.edu.nju.dislab.privacycollector;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.provider.BaseColumns;

import com.amap.api.location.AMapLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhantong on 2016/12/22.
 */

public class CollectorToDb {
    public static void writeAudio(SQLiteDatabase db, List<AudioData> data) {
        class Table implements BaseColumns {
            static final String TABLE_NAME = "audio";
            static final String COLUMN_NAME_AMPLITUDE = "amplitude";
            static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        }
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Table.TABLE_NAME + " (" +
                        Table._ID + " INTEGER PRIMARY KEY," +
                        Table.COLUMN_NAME_AMPLITUDE + " REAL," +
                        Table.COLUMN_NAME_TIMESTAMP + " INTEGER)";
        db.execSQL(SQL_CREATE_TABLE);
        if (data == null) {
            return;
        }
        for (AudioData audioData : data) {
            ContentValues values = new ContentValues();
            values.put(Table.COLUMN_NAME_AMPLITUDE, audioData.getAmplitude());
            values.put(Table.COLUMN_NAME_TIMESTAMP, audioData.getTimestamp());
            db.insert(Table.TABLE_NAME, null, values);
        }
    }

    public static void writeCallLog(SQLiteDatabase db, List<CallLogData> data) {
        class Table implements BaseColumns {
            static final String TABLE_NAME = "call_log";
            static final String COLUMN_NAME_NUMBER = "number";
            static final String COLUMN_NAME_TYPE = "type";
            static final String COLUMN_NAME_DATE = "date";
            static final String COLUMN_NAME_DURATION = "duration";
        }
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Table.TABLE_NAME + " (" +
                        Table._ID + " INTEGER PRIMARY KEY," +
                        Table.COLUMN_NAME_NUMBER + " TEXT," +
                        Table.COLUMN_NAME_TYPE + " TEXT," +
                        Table.COLUMN_NAME_DATE + " TEXT," +
                        Table.COLUMN_NAME_DURATION + " TEXT)";
        db.execSQL(SQL_CREATE_TABLE);
        if (data == null) {
            return;
        }
        for (CallLogData callLogData : data) {
            ContentValues values = new ContentValues();
            values.put(Table.COLUMN_NAME_NUMBER, callLogData.getNumber());
            values.put(Table.COLUMN_NAME_TYPE, callLogData.getType());
            values.put(Table.COLUMN_NAME_DATE, callLogData.getDate());
            values.put(Table.COLUMN_NAME_DURATION, callLogData.getDuration());
            db.insert(Table.TABLE_NAME, null, values);
        }
    }

    public static void writeContact(SQLiteDatabase db, List<ContactData> data) {
        class Table implements BaseColumns {
            static final String TABLE_NAME = "contact";
            static final String COLUMN_NAME_NAME = "name";
            static final String COLUMN_NAME_NUMBER = "number";
        }
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Table.TABLE_NAME + " (" +
                        Table._ID + " INTEGER PRIMARY KEY," +
                        Table.COLUMN_NAME_NAME + " TEXT," +
                        Table.COLUMN_NAME_NUMBER + " TEXT)";
        db.execSQL(SQL_CREATE_TABLE);
        if (data == null) {
            return;
        }
        for (ContactData contactData : data) {
            ContentValues values = new ContentValues();
            values.put(Table.COLUMN_NAME_NAME, contactData.getName());
            values.put(Table.COLUMN_NAME_NUMBER, contactData.getNumber());
            db.insert(Table.TABLE_NAME, null, values);
        }
    }

    public static void writeForegroundApp(SQLiteDatabase db, ForegroundAppData data) {
        class Table implements BaseColumns {
            static final String TABLE_NAME = "foreground_app";
            static final String COLUMN_NAME_PACKAGE_NAME = "package_name";
            static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        }
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Table.TABLE_NAME + " (" +
                        Table._ID + " INTEGER PRIMARY KEY," +
                        Table.COLUMN_NAME_PACKAGE_NAME + " TEXT," +
                        Table.COLUMN_NAME_TIMESTAMP + " INTEGER)";
        db.execSQL(SQL_CREATE_TABLE);
        if (data == null) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put(Table.COLUMN_NAME_PACKAGE_NAME, data.getPackageName());
        values.put(Table.COLUMN_NAME_TIMESTAMP, data.getTimestamp());
        db.insert(Table.TABLE_NAME, null, values);
    }

    public static void writeLocation(SQLiteDatabase db, AMapLocation data) {
        class Table implements BaseColumns {
            static final String TABLE_NAME = "location";
            static final String COLUMN_NAME_TYPE = "type";
            static final String COLUMN_NAME_LATITUDE = "latitude";
            static final String COLUMN_NAME_LONGITUDE = "longitude";
            static final String COLUMN_NAME_ACCURACY = "accuracy";
            static final String COLUMN_NAME_ADDRESS = "address";
            static final String COLUMN_NAME_TIME = "time";
            static final String COLUMN_NAME_AOI = "aoi";
        }
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Table.TABLE_NAME + " (" +
                        Table._ID + " INTEGER PRIMARY KEY," +
                        Table.COLUMN_NAME_TYPE + " INTEGER," +
                        Table.COLUMN_NAME_LATITUDE + " REAL," +
                        Table.COLUMN_NAME_LONGITUDE + " REAL," +
                        Table.COLUMN_NAME_ACCURACY + " REAL," +
                        Table.COLUMN_NAME_ADDRESS + " TEXT," +
                        Table.COLUMN_NAME_TIME + " INTEGER," +
                        Table.COLUMN_NAME_AOI + " TEXT)";
        db.execSQL(SQL_CREATE_TABLE);
        if (data == null) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put(Table.COLUMN_NAME_TYPE, data.getLocationType());
        values.put(Table.COLUMN_NAME_LATITUDE, data.getLatitude());
        values.put(Table.COLUMN_NAME_LONGITUDE, data.getLongitude());
        values.put(Table.COLUMN_NAME_ACCURACY, data.getAccuracy());
        values.put(Table.COLUMN_NAME_ADDRESS, data.getAddress());
        values.put(Table.COLUMN_NAME_TIME, data.getTime());
        values.put(Table.COLUMN_NAME_AOI, data.getAoiName());
        db.insert(Table.TABLE_NAME, null, values);
    }

    public static void writeRunningApp(SQLiteDatabase db, List<RunningAppData> data) {
        class Table implements BaseColumns {
            static final String TABLE_NAME = "running_app";
            static final String COLUMN_NAME_PACKAGE_NAME = "package_name";
            static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        }
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Table.TABLE_NAME + " (" +
                        Table._ID + " INTEGER PRIMARY KEY," +
                        Table.COLUMN_NAME_PACKAGE_NAME + " TEXT," +
                        Table.COLUMN_NAME_TIMESTAMP + " INTEGER)";
        db.execSQL(SQL_CREATE_TABLE);
        if (data == null) {
            return;
        }
        for (RunningAppData runningAppData : data) {
            ContentValues values = new ContentValues();
            values.put(Table.COLUMN_NAME_PACKAGE_NAME, runningAppData.getPackageName());
            values.put(Table.COLUMN_NAME_TIMESTAMP, runningAppData.getTimestamp());
            db.insert(Table.TABLE_NAME, null, values);
        }
    }

    public static void writeScreen(SQLiteDatabase db, ScreenData data) {
        class Table implements BaseColumns {
            static final String TABLE_NAME = "screen";
            static final String COLUMN_NAME_IS_SCREEN_ON = "is_screen_on";
            static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        }
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Table.TABLE_NAME + " (" +
                        Table._ID + " INTEGER PRIMARY KEY," +
                        Table.COLUMN_NAME_IS_SCREEN_ON + " INTEGER," +
                        Table.COLUMN_NAME_TIMESTAMP + " INTEGER)";
        db.execSQL(SQL_CREATE_TABLE);
        if (data == null) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put(Table.COLUMN_NAME_IS_SCREEN_ON, data.getIsScreenOn() ? 1 : 0);
        values.put(Table.COLUMN_NAME_TIMESTAMP, data.getTimestamp());
        db.insert(Table.TABLE_NAME, null, values);
    }

    public static void writeSensors(SQLiteDatabase db, Map<Integer, ArrayList<SensorData>> data) {
        class Table implements BaseColumns {
            static final String TABLE_NAME = "sensors";
            static final String COLUMN_NAME_TYPE = "type";
            static final String COLUMN_NAME_VALUE_0 = "value_0";
            static final String COLUMN_NAME_VALUE_1 = "value_1";
            static final String COLUMN_NAME_VALUE_2 = "value_2";
            static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        }
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Table.TABLE_NAME + " (" +
                        Table._ID + " INTEGER PRIMARY KEY," +
                        Table.COLUMN_NAME_TYPE + " INTEGER," +
                        Table.COLUMN_NAME_VALUE_0 + " REAL," +
                        Table.COLUMN_NAME_VALUE_1 + " REAL," +
                        Table.COLUMN_NAME_VALUE_2 + " REAL," +
                        Table.COLUMN_NAME_TIMESTAMP + " INTEGER)";
        db.execSQL(SQL_CREATE_TABLE);
        if (data == null) {
            return;
        }
        for (Map.Entry<Integer, ArrayList<SensorData>> entry : data.entrySet()) {
            int type = entry.getKey();
            ArrayList<SensorData> result = entry.getValue();
            for (SensorData sensorData : result) {
                ContentValues values = new ContentValues();
                values.put(Table.COLUMN_NAME_TYPE, type);
                float[] sensorValues = sensorData.getValues();
                values.put(Table.COLUMN_NAME_VALUE_0, sensorValues[0]);
                values.put(Table.COLUMN_NAME_VALUE_1, sensorValues[1]);
                values.put(Table.COLUMN_NAME_VALUE_2, sensorValues[2]);
                values.put(Table.COLUMN_NAME_TIMESTAMP, sensorData.getTimestamp());
                db.insert(Table.TABLE_NAME, null, values);
            }

        }
    }

    public static void writeSms(SQLiteDatabase db, List<SmsData> data) {
        class Table implements BaseColumns {
            static final String TABLE_NAME = "sms";
            static final String COLUMN_NAME_ADDRESS = "address";
            static final String COLUMN_NAME_TYPE = "type";
            static final String COLUMN_NAME_DATE = "date";
            static final String COLUMN_NAME_PERSON = "person";
        }
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Table.TABLE_NAME + " (" +
                        Table._ID + " INTEGER PRIMARY KEY," +
                        Table.COLUMN_NAME_ADDRESS + " TEXT," +
                        Table.COLUMN_NAME_TYPE + " TEXT," +
                        Table.COLUMN_NAME_DATE + " TEXT," +
                        Table.COLUMN_NAME_PERSON + " TEXT)";
        db.execSQL(SQL_CREATE_TABLE);
        if (data == null) {
            return;
        }
        for (SmsData smsData : data) {
            ContentValues values = new ContentValues();
            values.put(Table.COLUMN_NAME_ADDRESS, smsData.getAddress());
            values.put(Table.COLUMN_NAME_TYPE, smsData.getType());
            values.put(Table.COLUMN_NAME_DATE, smsData.getDate());
            values.put(Table.COLUMN_NAME_PERSON, smsData.getPerson());
            db.insert(Table.TABLE_NAME, null, values);
        }
    }

    public static void writeWifi(SQLiteDatabase db, List<ScanResult> data) {
        class Table implements BaseColumns {
            static final String TABLE_NAME = "wifi";
            static final String COLUMN_NAME_BSSID = "bssid";
            static final String COLUMN_NAME_SSID = "ssid";
            static final String COLUMN_NAME_LEVEL = "level";
            static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        }
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Table.TABLE_NAME + " (" +
                        Table._ID + " INTEGER PRIMARY KEY," +
                        Table.COLUMN_NAME_BSSID + " TEXT," +
                        Table.COLUMN_NAME_SSID + " TEXT," +
                        Table.COLUMN_NAME_LEVEL + " INTEGER," +
                        Table.COLUMN_NAME_TIMESTAMP + " INTEGER)";
        db.execSQL(SQL_CREATE_TABLE);
        if (data == null) {
            return;
        }
        for (ScanResult scanResult : data) {
            ContentValues values = new ContentValues();
            values.put(Table.COLUMN_NAME_BSSID, scanResult.BSSID);
            values.put(Table.COLUMN_NAME_SSID, scanResult.SSID);
            values.put(Table.COLUMN_NAME_LEVEL, scanResult.level);
            values.put(Table.COLUMN_NAME_TIMESTAMP, scanResult.timestamp);
            db.insert(Table.TABLE_NAME, null, values);
        }
    }
}
