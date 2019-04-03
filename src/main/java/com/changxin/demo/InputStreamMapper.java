package com.changxin.demo;

import java.io.InputStream;
import java.util.Iterator;
import java.util.function.Function;

public interface InputStreamMapper<T> extends Function<InputStream, Iterator> {
}
