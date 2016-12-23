package cn.edu.nju.dislab.privacycollector;

/**
 * Created by zhantong on 2016/12/22.
 */

public class AudioData {
    private long timestamp;
    private double amplitude;

    public AudioData(long timestamp, double amplitude) {
        this.timestamp = timestamp;
        this.amplitude = amplitude;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return timestamp + " " + amplitude;
    }
}
