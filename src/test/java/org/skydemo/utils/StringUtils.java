package org.skydemo.utils;

public class StringUtils {

    public static String randomString() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
    }
}
