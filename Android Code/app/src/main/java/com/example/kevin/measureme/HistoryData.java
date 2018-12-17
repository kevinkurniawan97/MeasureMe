package com.example.kevin.measureme;

/**
 * Created by Kevin on 7/9/2018.
 */

public class HistoryData {
    private int id;
    private String name;
    private String datetime;

    public HistoryData(int id, String name, String datetime) {
        this.id=id;
        this.name=name;
        this.datetime=datetime;
    }

    public void setId(int id) {
        this.id=id;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setDatetime(String datetime) {
        this.datetime=datetime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDatetime() {
        return datetime;
    }
}
