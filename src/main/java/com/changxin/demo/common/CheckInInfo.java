package com.changxin.demo.common;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
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


    public CheckInInfo(String data, String arrive, String depart, Hyperlink arrivePic, Hyperlink departPic) {
        this.date = data;
        this.arrive = arrive;
        this.depart = depart;
        this.arrivePic = arrivePic;
        this.departPic = departPic;
    }

    public List<Object> toList() {
        return Arrays.asList(date, arrive, depart, arrivePic, departPic);
    }

    public void addHyperLink(Hyperlink link) {
        if(link == null) {
            return;
        }

        if(StringUtils.isNotBlank(arrive) && StringUtils.split(arrive)[0].equals(link.getLabel())) {
            this.setArrivePic(link);
        }
        if(StringUtils.isNotBlank(depart) && StringUtils.split(depart)[0].equals(link.getLabel())) {
            this.setDepartPic(link);
        }
    }



}
