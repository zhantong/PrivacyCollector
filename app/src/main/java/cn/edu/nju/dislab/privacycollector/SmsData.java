package cn.edu.nju.dislab.privacycollector;

/**
 * Created by zhantong on 2016/12/22.
 */

public class SmsData {
    private String address;
    private String type;
    private String date;
    private String person;

    public SmsData(String address, String type, String date, String person) {
        this.address = address;
        this.type = type;
        this.date = date;
        this.person = person;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return address + " " + type + " " + date + " " + person;
    }
}
