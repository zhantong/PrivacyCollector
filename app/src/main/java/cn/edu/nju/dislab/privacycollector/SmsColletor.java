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

public class SmsColletor {
    private static final String TAG = "SmsCollector";
    private static final String[] PERMISSIONS = {Manifest.permission.READ_SMS};
    private Context mContext;
    private ContentResolver mContentResolver;
    private List<String[]> results;

    public SmsColletor() {
        this(MainApplication.getContext());
    }

    public SmsColletor(Context context) {
        mContext = context;
        mContentResolver = mContext.getContentResolver();
    }

    public void collect() {
        Cursor cursor = mContentResolver.query(Telephony.Sms.Inbox.CONTENT_URI, null, null, null, null);
        Log.i(TAG, "start: " + cursor.toString());
        if (cursor == null) {
            Log.i(TAG, "null cursor");
            return;
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
            return;
        }
        Log.i(TAG, "end: " + cursor.toString());
        cursor.close();
    }

    public List<String[]> getResult() {
        return results;
    }

    public static String[] getPermissions() {
        return PERMISSIONS;
    }
}
