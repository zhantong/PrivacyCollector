package cn.edu.nju.dislab.privacycollector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.Telephony;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhantong on 2016/12/21.
 */

public class SmsCollector {
    private static final String TAG = "SmsCollector";
    private static final String[] PERMISSIONS = {Manifest.permission.READ_SMS};
    private Context mContext;
    private ContentResolver mContentResolver;
    private List<String[]> results;

    public SmsCollector() {
        this(MainApplication.getContext());
    }

    public SmsCollector(Context context) {
        mContext = context;
        mContentResolver = mContext.getContentResolver();
    }

    public int collect() {
        if (!EasyPermissions.hasPermissions(PERMISSIONS)) {
            return Collector.NO_PERMISSION;
        }
        Cursor cursor;
        try {
            cursor = mContentResolver.query(Telephony.Sms.Inbox.CONTENT_URI, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return Collector.NO_PERMISSION;
        }
        if (cursor == null) {
            Log.i(TAG, "null cursor");
            return Collector.NO_PERMISSION;
        }
        if (cursor.getCount() > 0) {
            results = new ArrayList<>();
            while (cursor.moveToNext()) {
                String address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
                String type = cursor.getString(cursor.getColumnIndex(Telephony.Sms.TYPE));
                String date = cursor.getString(cursor.getColumnIndex(Telephony.Sms.DATE));
                String person = cursor.getString(cursor.getColumnIndex(Telephony.Sms.PERSON));
                results.add(new String[]{address, type, date, person});
            }
        } else {
            Log.i(TAG, "empty cursor");
            return Collector.COLLECT_FAILED;
        }
        cursor.close();
        return Collector.COLLECT_SUCCESS;
    }

    public List<String[]> getResult() {
        return results;
    }

    public static String[] getPermissions() {
        return PERMISSIONS;
    }
}
