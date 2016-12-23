package cn.edu.nju.dislab.privacycollector;

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

    public boolean getIsScreenOn() {
        return isScreenOn;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return timestamp + " " + isScreenOn;
    }
}
