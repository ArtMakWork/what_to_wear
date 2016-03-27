package com.artmakdeveloper.whattoweartoday.WeatherUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.artmakdeveloper.whattoweartoday.DBUtils.DBHelper;
import com.artmakdeveloper.whattoweartoday.DataForTest.TestData;
import com.artmakdeveloper.whattoweartoday.MainActivity;
import com.artmakdeveloper.whattoweartoday.MyLocationUtils.MyLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyWeather implements IMyWeather {

    DBHelper dbHelper;
    Date date;
    public long giveWeather(String nameItem,Context context) throws JSONException {
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long res, temper;
        String dt, strDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String today = simpleDateFormat.format(new Date(System.currentTimeMillis()));

        JSONObject jsonRootObject = new JSONObject(dbHelper.getLastText(db));
        JSONArray jsonArrayList = jsonRootObject.getJSONArray("list");
        for (int i = 0; i<jsonArrayList.length(); i++) {
            JSONObject jsonObjectDT = jsonArrayList.getJSONObject(0);
            JSONObject jsonArrayTemp = jsonObjectDT.getJSONObject("temp");
            dt = jsonObjectDT.optString("dt");
            temper = jsonArrayTemp.optInt(nameItem);
            res = Long.parseLong(dt) * 1000;
            date = new Date(res);
            strDate = simpleDateFormat.format(date);
            if(String.valueOf(today).equals(strDate)){
                return temper;
            }
        }
        return 0;
    }

    public String giveWeatherIcon (Context context) throws JSONException {
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long res;
        String dt, strDate,temper;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String today = simpleDateFormat.format(new Date(System.currentTimeMillis()));

        JSONObject jsonRootObject = new JSONObject(dbHelper.getLastText(db));
        JSONArray jsonArrayList = jsonRootObject.getJSONArray("list");
        for (int i = 0; i<jsonArrayList.length(); i++) {
            JSONObject jsonObjectDT = jsonArrayList.getJSONObject(i);
            JSONArray jsonArrayWeather = jsonObjectDT.getJSONArray("weather");
            JSONObject jsonObjectIcon = jsonArrayWeather.getJSONObject(0);
            temper = jsonObjectIcon.optString("icon");
            dt = jsonObjectDT.optString("dt");
            res = Long.parseLong(dt) * 1000;
            date = new Date(res);
            strDate = simpleDateFormat.format(date);
            if (String.valueOf(today).equals(strDate)) {
                return String.valueOf(temper);
            }
        }
        return null;
    }

}
