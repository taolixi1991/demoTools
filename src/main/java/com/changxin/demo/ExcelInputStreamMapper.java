package com.changxin.demo;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;

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
        ExcelInputStreamMapper mapper = ExcelInputStreamMapper.newMapper(new Sheet(1, 1));
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("指纹机.xlsx");
        Iterator it = mapper.apply(inputStream);

        int i = 0;
        //it.next();

        while(it.hasNext() && i < 5) {
            List<String> ob = (List<String>)it.next();
            //System.out.println(ob.get(3) + "==" + ob.get(5) + "==" + ob.get(9) + "==" + ob.get(10));


            i++;
            System.out.println(ob.toString());
            System.out.println("=====");
        }

    }

}
