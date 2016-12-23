package cn.edu.nju.dislab.privacycollector;

/**
 * Created by zhantong on 2016/12/22.
 */

public class CallLogData {
    private String number;
    private String type;
    private String date;
    private String duration;

    public CallLogData(String number, String type, String date, String duration) {
        this.number = number;
        this.type = type;
        this.date = date;
        this.duration = duration;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return date + " " + number + " " + type + " " + duration;
    }
}
