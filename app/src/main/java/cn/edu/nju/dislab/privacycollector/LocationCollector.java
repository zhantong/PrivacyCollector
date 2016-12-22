package cn.edu.nju.dislab.privacycollector;


import android.Manifest;
import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by zhantong on 2016/12/21.
 */

public class LocationCollector {
    private static final String TAG = "LocationCollector";
    private static final String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET};
    private AMapLocationClient mLocationClient = null;
    private Context mContext;
    private final Object LOCK = new Object();
    private AMapLocation result;
    private AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            result = aMapLocation;
            synchronized (LOCK) {
                LOCK.notify();
            }
        }
    };

    public LocationCollector() {
        this(MainApplication.getContext());
    }

    public LocationCollector(Context context) {
        mContext = context;
        mLocationClient = new AMapLocationClient(mContext);
        mLocationClient.setLocationListener(mLocationListener);
    }

    public void collect() {
        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //locationClientOption.setOnceLocation(true);
        locationClientOption.setOnceLocationLatest(true);
        locationClientOption.setHttpTimeOut(6000);
        locationClientOption.setLocationCacheEnable(false);

        mLocationClient.setLocationOption(locationClientOption);
        mLocationClient.startLocation();

        synchronized (LOCK) {
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }

        mLocationClient.unRegisterLocationListener(mLocationListener);
        mLocationClient.stopLocation();
        mLocationClient.onDestroy();
    }

    public AMapLocation getResult() {
        return result;
    }

    public static String[] getPermissions() {
        return PERMISSIONS;
    }
}
