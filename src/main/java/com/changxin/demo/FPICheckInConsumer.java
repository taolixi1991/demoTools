package com.changxin.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class FPICheckInConsumer implements Consumer {

    private static List<String> result = new ArrayList<>();

    @Override
    public void accept(Object o) {
        List<String> record = (List<String>)o;
        result.add(record.get(3));
        result.add(record.get(5));
        //判断值
        result.add(record.get(9));
        result.add(record.get(10));
    }

    public List<String> getResult() {
        return result;
    }
}
