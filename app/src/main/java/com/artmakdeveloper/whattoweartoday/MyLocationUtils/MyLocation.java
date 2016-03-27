package com.artmakdeveloper.whattoweartoday.MyLocationUtils;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.artmakdeveloper.whattoweartoday.DBUtils.DBHelper;
import com.artmakdeveloper.whattoweartoday.DataForTest.TestData;
import com.artmakdeveloper.whattoweartoday.WeatherUtils.WeatherHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

public class MyLocation{

    DBHelper dbHelper;

    public String giveMeLocName(Context context) throws JSONException {
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        JSONObject jsonRootObject = new JSONObject(dbHelper.getLastText(db));
        JSONObject headItemJsonArray = jsonRootObject.getJSONObject("city");
        String city = headItemJsonArray.optString("name");
        String country = headItemJsonArray.optString("country");
        return city +", "+country;
    }

}
