package com.changxin.demo.extractor;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class ExcelInputStreamMapper<T> implements InputStreamMapper<T> {

    public static ExcelInputStreamMapper newMapper(Sheet sheet) {
        return new ExcelInputStreamMapper<>(sheet);
    }

    private Sheet sheet;

    private ExcelInputStreamMapper(Sheet sheet) {
        this.sheet = sheet;
    }

    @Override
    public Iterator<T> apply(InputStream inputStream) {
        List<T> data = (List<T>)EasyExcelFactory.read(inputStream, sheet);
        return data.iterator();
    }


    public static void main(String[] args) {
        ExcelInputStreamMapper mapper = ExcelInputStreamMapper.newMapper(new Sheet(1, 3));
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("钉钉签到报表.xls");
        Iterator it = mapper.apply(inputStream);

        int i = 0;
        //it.next();

        while(it.hasNext() && i < 5) {
            List<Object> ob = (List<Object>)it.next();
            System.out.println(ob.get(0) + "==" + ob.get(5) + "==" + ob.get(6) + "==" + ob.get(10));
            i++;
//            System.out.println(ob.toString());
//            System.out.println("=====");
        }

    }

}
