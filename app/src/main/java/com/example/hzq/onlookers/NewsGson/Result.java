package com.example.hzq.onlookers.NewsGson;

import java.util.List;

public class Result {
    private String start;
    List<Data> data;
    public String getStart() {
        return start;
    }
    public void setStart(String stat) {
        this.start = start;
    }
    public List<Data> getData() {
        return data;
    }
    public void setData(List<Data> data) {
        this.data = data;
    }
}
