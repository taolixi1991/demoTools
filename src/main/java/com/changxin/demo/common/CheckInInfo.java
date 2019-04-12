package com.changxin.demo.common;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Hyperlink;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class CheckInInfo {
    private String date;

    //0:ontime， 1：late，2 absence
    private String arrive;
    private String depart;

    private Hyperlink arrivePic;
    private Hyperlink departPic;


    public CheckInInfo(String data, String arrive, String depart) {
        this.date = data;
        this.arrive = arrive;
        this.depart = depart;
    }

    public List<Object> toList() {
        return Arrays.asList(date, arrive, depart);
    }

    public void addHyperLink(Hyperlink link) {
        if(link == null) {
            return;
        }

        if(arrive.equals(link.getLabel())) {
            this.arrivePic = link;
        }
        if(depart.equals(link.getLabel())) {
            this.departPic = link;
        }
    }

}
