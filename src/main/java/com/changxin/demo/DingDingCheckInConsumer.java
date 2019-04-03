package com.changxin.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DingDingCheckInConsumer implements Consumer {

    private static List<String> result = new ArrayList<>();

    @Override
    public void accept(Object o) {

    }

    public List<String> getResult() {
        return result;
    }
}
