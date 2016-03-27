package com.artmakdeveloper.whattoweartoday;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.artmakdeveloper.whattoweartoday.DBUtils.DBHelper;
import com.artmakdeveloper.whattoweartoday.MyDateUtils.MyDate;
import com.artmakdeveloper.whattoweartoday.MyLocationUtils.MyLocation;
import com.artmakdeveloper.whattoweartoday.WearUtils.MyWear;
import com.artmakdeveloper.whattoweartoday.WeatherUtils.MyWeather;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG ="myLogs";
    Button btnRefresh;
    TextView tv_date;
    TextView tv_minT;
    TextView tv_maxT;
    TextView headitem;
    TextView bodyitem;
    TextView legsitem;
    TextView shoesitem;
    TextView tv_location;
    TextView tv_info;
    ImageView iv_wearther,iv_woman;
    int minT, maxT;

    DBHelper dbHelper;
    MyDate myDate = new MyDate();
    MyLocation myLocation = new MyLocation();
    MyWeather myWeather = new MyWeather();
    MyWear myWear = new MyWear();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    Log.d(LOG_TAG, "- - - Start MainActivity - - - ");
        btnRefresh = (Button) findViewById(R.id.bnt_refresh);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_minT = (TextView) findViewById(R.id.tv_min_t);
        tv_maxT = (TextView) findViewById(R.id.tv_max_t);
        headitem = (TextView) findViewById(R.id.tv_head_item);
        bodyitem = (TextView) findViewById(R.id.tv_body_item);
        legsitem = (TextView) findViewById(R.id.tv_legs_Item);
        shoesitem = (TextView) findViewById(R.id.tv_shoes_item);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_info = (TextView) findViewById(R.id.tv_info);
        iv_wearther = (ImageView) findViewById(R.id.weatherImage);
        iv_woman = (ImageView) findViewById(R.id.womanImage);
        tv_date.setText(myDate.getNowData());
        btnRefresh.setVisibility(View.VISIBLE);


        try {
            setMyWeatherJSON();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            setMyWearJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }





        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DBHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                dbHelper.delLastLine(db);
                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    public Context getContext() {
        return getApplicationContext();
    }

    private void setMyWeatherJSON() throws JSONException {
        Log.d(LOG_TAG, "- - - Start setMyWearJson - - -");
        minT = (int) myWeather.giveWeather("min",this);
        maxT = (int) myWeather.giveWeather("max",this);
        tv_minT.setText(String.valueOf(minT)+ " C°");
        tv_maxT.setText(String.valueOf(maxT) + " C°");
        tv_location.setText(myLocation.giveMeLocName(this));
        iv_wearther.setImageResource(getResources().getIdentifier(returnImgName(myWeather.giveWeatherIcon(this)),
                "drawable", getPackageName()));
    }

    private void setMyWearJSON() throws JSONException {
        headitem.setText(myWear.giveItem(minT, maxT, "HeadItem"));
        bodyitem.setText(myWear.giveItem(minT, maxT, "BodyItem"));
        legsitem.setText(myWear.giveItem(minT, maxT, "LegsItem"));
        shoesitem.setText(myWear.giveItem(minT, maxT, "ShoesItem"));
        iv_woman .setImageResource(getResources().getIdentifier(myWear.giveItem(minT, maxT, "WomanIcon"),
                "drawable", getPackageName()));
    }

    private String returnImgName (String name) {
        String path = null;
    Log.d(LOG_TAG, "hour:" + String.valueOf(myDate.getNowHour()));
        if((6<=myDate.getNowHour())&&(myDate.getNowHour()<=19)){
            switch (name) {
                case ("01d"):path = "day_sunny";break;
                case ("02d"):path = "day_few_clouds";break;
                case ("03d"):path = "day_f_overcast";break;
                case ("04d"):path = "day_s_overcast";break;
                case ("09d"):path = "day_f_heavy";break;
                case ("10d"):path = "day_s_heavy";break;
                case ("11d"):path = "day_storm";break;
                case ("13d"):path = "day_snow";break;
                case ("50d"):path = "day_fog";break;
                case ("01n"):path = "day_sunny";break;
                case ("02n"):path = "day_few_clouds";break;
                case ("03n"):path = "day_f_overcast";break;
                case ("04n"):path = "day_s_overcast";break;
                case ("09n"):path = "day_f_heavy";break;
                case ("10n"):path = "day_s_heavy";break;
                case ("11n"):path = "day_storm";break;
                case ("13n"):path = "day_snow";break;
                case ("50n"):path = "day_fog";break;
                case ("null"):path = "alert";break;
                }
        }else{
            switch (name) {
                case ("01d"):path = "night_sunny";break;
                case ("02d"):path = "night_few_clouds";break;
                case ("03d"):path = "night_f_overcast";break;
                case ("04d"):path = "night_s_overcast";break;
                case ("09d"):path = "night_f_heavy";break;
                case ("10d"):path = "night_s_heavy";break;
                case ("11d"):path = "night_storm";break;
                case ("13d"):path = "night_snow";break;
                case ("50d"):path = "night_fog";break;
                case ("01n"):path = "night_sunny";break;
                case ("02n"):path = "night_few_clouds";break;
                case ("03n"):path = "night_f_overcast";break;
                case ("04n"):path = "night_s_overcast";break;
                case ("09n"):path = "night_f_heavy";break;
                case ("10n"):path = "night_s_heavy";break;
                case ("11n"):path = "night_storm";break;
                case ("13n"):path = "night_snow";break;
                case ("50n"):path = "night_fog";break;
                case ("null"):path = "alert";break;
            }
        }
        return path;
    }

}
