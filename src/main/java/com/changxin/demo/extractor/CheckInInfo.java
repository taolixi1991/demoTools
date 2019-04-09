package com.changxin.demo.extractor;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class CheckInInfo {
    private String date;

    //0:ontime， 1：late，2 absence
    private String arrive;
    private String depart;

    private Object picture1;
    private Object picture2;
    private Object picture3;


    public CheckInInfo(String data, String arrive, String depart, Object picture1, Object picture2, Object picture3) {
        this.date = data;
        this.arrive = arrive;
        this.depart = depart;
        this.picture1 = picture1;
        this.picture2 = picture2;
        this.picture3 = picture3;
    }

    public List<Object> toList() {
        return Arrays.asList(date, arrive, depart, picture1, picture2, picture3);
    }

}
