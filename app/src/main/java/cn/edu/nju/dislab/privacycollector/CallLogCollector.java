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
    private List<CallLogData> results;

    public CallLogCollector() {
        this(MainApplication.getContext());
    }

    public CallLogCollector(Context context) {
        mContext = context;
        mContentResolver = mContext.getContentResolver();
    }

    public int collect() {
        if (!EasyPermissions.hasPermissions(PERMISSIONS)) {
            return Collector.NO_PERMISSION;
        }
        Cursor cursor;
        try {
            cursor = mContentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, null);
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
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                String type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                String date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                results.add(new CallLogData(number, type, date, duration));
            }
        } else {
            Log.i(TAG, "empty cursor");
            return Collector.COLLECT_FAILED;
        }
        cursor.close();
        return Collector.COLLECT_SUCCESS;
    }

    public List<CallLogData> getResult() {
        return results;
    }

    public static String[] getPermissions() {
        return PERMISSIONS;
    }
}
