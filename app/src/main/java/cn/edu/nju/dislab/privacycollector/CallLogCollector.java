package cn.edu.nju.dislab.privacycollector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhantong on 2016/12/21.
 */

public class CallLogCollector {
    private static final String TAG = "ContactCollector";
    private static final String[] PERMISSIONS = {Manifest.permission.READ_CALL_LOG};
    private Context mContext;
    private ContentResolver mContentResolver;
    private List<String[]> results;

    public CallLogCollector() {
        this(MainApplication.getContext());
    }

    public CallLogCollector(Context context) {
        mContext = context;
        mContentResolver = mContext.getContentResolver();
    }

    public void collect() {
        Cursor cursor = mContentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        Log.i(TAG, "start: " + cursor.toString());
        if (cursor == null) {
            Log.i(TAG, "null cursor");
            return;
        }
        if (cursor.getCount() > 0) {
            results = new ArrayList<>();
            while (cursor.moveToNext()) {
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                String type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                String date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                results.add(new String[]{number, type, date, duration});
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
