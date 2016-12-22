package cn.edu.nju.dislab.privacycollector;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import android.view.Display;

import static android.content.Context.POWER_SERVICE;

/**
 * Created by zhantong on 2016/12/22.
 */

public class ScreenCollector {
    private static final String TAG = "ScreenCollector";
    private Context mContext;
    private DisplayManager mDisplayManager;
    private PowerManager mPowerManager;
    private boolean result;

    public ScreenCollector() {
        this(MainApplication.getContext());
    }

    public ScreenCollector(Context context) {
        mContext = context;
        mDisplayManager = (DisplayManager) mContext.getSystemService(Context.DISPLAY_SERVICE);
        mPowerManager = (PowerManager) mContext.getSystemService(POWER_SERVICE);
        if (mDisplayManager == null) {
            Log.i(TAG, "null DisplayManager");
            throw new RuntimeException();
        }
    }

    public void collect() {
        result = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            for (Display display : mDisplayManager.getDisplays()) {
                if (display.getState() != Display.STATE_OFF) {
                    result = true;
                }
            }
        } else {
            result = mPowerManager.isScreenOn();
        }
    }

    public boolean getResult() {
        return result;
    }
}
