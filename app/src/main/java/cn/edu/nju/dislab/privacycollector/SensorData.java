package cn.edu.nju.dislab.privacycollector;

import java.util.Arrays;

/**
 * Created by zhantong on 2016/12/22.
 */

public class SensorData {
    public long timestamp;
    public float[] values;

    public SensorData(long timestamp, float[] values) {
        this.timestamp = timestamp;
        this.values = values;
    }

    @Override
    public String toString() {
        return timestamp + " " + Arrays.toString(values);
    }
}
