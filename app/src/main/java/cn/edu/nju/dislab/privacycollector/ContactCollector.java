package cn.edu.nju.dislab.privacycollector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhantong on 2016/12/21.
 */

public class ContactCollector {
    private static final String TAG = "ContactCollector";
    private static final String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS};
    private Context mContext;
    private ContentResolver mContentResolver;
    private List<String[]> results;

    public ContactCollector() {
        this(MainApplication.getContext());
    }

    public ContactCollector(Context context) {
        mContext = context;
        mContentResolver = mContext.getContentResolver();
    }

    public void collect() {
        Cursor cursor = mContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        Log.i(TAG, "start: " + cursor.toString());
        if (cursor == null) {
            Log.i(TAG, "null cursor");
            return;
        }
        if (cursor.getCount() > 0) {
            results = new ArrayList<>();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                results.add(new String[]{name, number});
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
