package com.changxin.demo;

import lombok.Getter;

@Getter
public class CheckInInfo {
    private String date;

    //0:ontime， 1：late，2 absence
    private String arrive;
    private String depart;

    public CheckInInfo(String data, String arrive, String depart) {
        this.date = data;
        this.arrive = arrive;
        this.depart = depart;
    }
}
