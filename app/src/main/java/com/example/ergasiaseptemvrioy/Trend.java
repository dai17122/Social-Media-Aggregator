package com.example.ergasiaseptemvrioy;

import android.util.Log;

public class Trend {

    private String name;
    private String queryUrl;

    public Trend setName(String name) {
        this.name = name;
        return  this;
    }

    public Trend setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    @Override
    public String toString() {
        return "Trend{" +
            "name='" + name + '\'' +
            ", queryUrl='" + queryUrl + '\'' +
            '}';
    }
}
