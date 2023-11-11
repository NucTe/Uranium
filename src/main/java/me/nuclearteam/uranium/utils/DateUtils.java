package me.nuclearteam.uranium.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

    public static String getCurrent() {
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public static String monthLater() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return dateFormat.format(calendar.getTime());
    }

    public static boolean hasExpired(String expiresOn) {
        if (expiresOn.equalsIgnoreCase("forever")) return false;
        try {
            Date dateOnExpires = dateFormat.parse(expiresOn);
            Date dateNow = Calendar.getInstance().getTime();
            return dateNow.getTime() >= dateOnExpires.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

}
