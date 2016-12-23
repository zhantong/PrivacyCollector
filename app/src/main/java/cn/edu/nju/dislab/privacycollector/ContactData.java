package cn.edu.nju.dislab.privacycollector;

/**
 * Created by zhantong on 2016/12/22.
 */

public class ContactData {
    private String name;
    private String number;

    public ContactData(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return name + " " + number;
    }
}
