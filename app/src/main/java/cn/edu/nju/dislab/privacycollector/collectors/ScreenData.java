package cn.edu.nju.dislab.privacycollector.collectors;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by zhantong on 2016/12/22.
 */

public class ScreenData {
    private boolean isScreenOn;
    private long timestamp;

    public ScreenData(boolean isScreenOn, long timestamp) {
        this.isScreenOn = isScreenOn;
        this.timestamp = timestamp;
    }

    public void toDb(SQLiteDatabase db) {
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
        ContentValues values = new ContentValues();
        values.put(Table.COLUMN_NAME_IS_SCREEN_ON, isScreenOn ? 1 : 0);
        values.put(Table.COLUMN_NAME_TIMESTAMP, timestamp);
        db.insert(Table.TABLE_NAME, null, values);
    }

    @Override
    public String toString() {
        return timestamp + " " + isScreenOn;
    }
}
