package com.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tool {
    public static String fmtDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return  format.format(date);
    }

    public static String fmtTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HHmmss");
        return format.format(date);
    }
}
