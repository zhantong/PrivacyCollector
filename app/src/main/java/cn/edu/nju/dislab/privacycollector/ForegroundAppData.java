package cn.edu.nju.dislab.privacycollector;

/**
 * Created by zhantong on 2016/12/22.
 */

public class ForegroundAppData {
    private String packageName;
    private long timestamp;

    public ForegroundAppData(String packageName, long timestamp) {
        this.packageName = packageName;
        this.timestamp = timestamp;
    }

    public String getPackageName() {
        return packageName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return timestamp + " " + packageName;
    }
}
