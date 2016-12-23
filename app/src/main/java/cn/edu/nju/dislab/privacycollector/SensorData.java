package cn.edu.nju.dislab.privacycollector;

import java.util.Arrays;

/**
 * Created by zhantong on 2016/12/22.
 */

public class SensorData {
    private long timestamp;
    private float[] values;

    public SensorData(long timestamp, float[] values) {
        this.timestamp = timestamp;
        this.values = values;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public float[] getValues() {
        return values;
    }

    @Override
    public String toString() {
        return timestamp + " " + Arrays.toString(values);
    }
}
