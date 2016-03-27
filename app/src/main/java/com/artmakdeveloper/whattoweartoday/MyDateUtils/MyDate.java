package com.artmakdeveloper.whattoweartoday.MyDateUtils;

        import java.text.SimpleDateFormat;
        import java.util.Date;

/**
 * Created by HOME on 03.03.2016.
 */
public class MyDate {

    public String getNowData(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        return date;
    }

    public int getNowHour(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        int date = Integer.valueOf(simpleDateFormat.format(new Date(System.currentTimeMillis())));
        return date;
    }

}
