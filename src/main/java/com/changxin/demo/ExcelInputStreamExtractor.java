package com.changxin.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Optional;
import java.util.prefs.BackingStoreException;

@SuppressWarnings("unchecked")
public class ExcelInputStreamExtractor<T> {
    private final InputStreamMapper<T> inputStreamMapper;
    private InputStream inputStream;

    private Iterator<T> mapperIterator;

    public static <T> ExcelInputStreamExtractor<T> of(InputStream inputStream,
                                                      InputStreamMapper<T> inputStreamMapper) {
        return new ExcelInputStreamExtractor(inputStream, inputStreamMapper);
    }

    public void open() {
        mapperIterator = inputStreamMapper.apply(inputStream);
    }

    public Optional<T> next() throws BackingStoreException {
        if(inputStream == null || mapperIterator == null) {
            throw new IllegalStateException("Unable to do iteration");
        }

        try {
            if(mapperIterator.hasNext()) {
                T nextObject = mapperIterator.next();
                return Optional.of(nextObject);
            } else {
                return Optional.empty();
            }
        } catch (RuntimeException e) {
            throw new BackingStoreException(e);
        }
    }

    public boolean hasNext() {
        return mapperIterator.hasNext();
    }

    public void close() throws IOException {
        if(inputStream != null) {
            inputStream.close();
        }
    }

    private ExcelInputStreamExtractor(InputStream inputStream, InputStreamMapper<T> inputStreamMapper){
        this.inputStream = inputStream;
        this.inputStreamMapper = inputStreamMapper;
    }


    public static void main(String[] args) {
    }
}
