package com.changxin.demo.utils;

public enum CheckInStatus {
    ONTIME(0),
    OVERDUE(1),
    ABSENCE(2);

    private int num;

    CheckInStatus(int num) {
        this.num = num;
    }

    public int value() {
        return num;
    }

}
