package com.pupccis.fitnex.api;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public static String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        return sdf.format(date);
    }
}
