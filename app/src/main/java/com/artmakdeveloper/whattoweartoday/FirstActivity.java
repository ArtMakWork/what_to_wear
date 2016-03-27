package com.artmakdeveloper.whattoweartoday;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.artmakdeveloper.whattoweartoday.DBUtils.DBHelper;
import com.artmakdeveloper.whattoweartoday.MyDateUtils.MyDate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class FirstActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";
    Button openSettings;
    TextView set_inf,info;
    ProgressBar myPB;
    MyDate nowDate = new MyDate();
    DBHelper dbHelper;
    LocationManager locationManager;
    double loc_longt,loc_latit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
    Log.d(LOG_TAG, "Start first activity");
        dbHelper = new DBHelper(this);
    Log.d(LOG_TAG, "db helper create");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    Log.d(LOG_TAG, "db = dbHelper.getWritableDatabase()");
        String last_date_in_BD = dbHelper.getLastLine(db);
    Log.d(LOG_TAG, "last_date_in_BD = dbHelper.getLastLine(db)");
    Log.d(LOG_TAG, last_date_in_BD);
    Log.d(LOG_TAG, "---finish db.lastline---");
        if (last_date_in_BD.equals(nowDate.getNowData())){
    Log.d(LOG_TAG, last_date_in_BD+".equals("+nowDate.getNowData());
            Intent intent = new Intent(FirstActivity.this, MainActivity.class);
    Log.d(LOG_TAG, "Go to MainActivity");
            startActivity(intent);
    Log.d(LOG_TAG, "finish First Activity");
            finish();

        }else{
    Log.d(LOG_TAG, "No");
            //Toast.makeText(this,"HET",Toast.LENGTH_SHORT).show();
        }


        set_inf=(TextView)findViewById(R.id.tv_settings);
        info = (TextView)findViewById(R.id.textView2);


        myPB = (ProgressBar) findViewById(R.id.progressBar);
        openSettings = (Button) findViewById(R.id.btn_settings);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);



    }

    @Override
    protected void onResume() {
        super.onResume();
    Log.d(LOG_TAG, "on Reasume - start");
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,
                1 * 1000, 100, locationListener);
    Log.d(LOG_TAG, "Passive provider - requestLocationUpdates");
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                1 * 1000, 100, locationListener);
    Log.d(LOG_TAG, "Network provider - requestLocationUpdates");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1 * 1000, 100, locationListener);
    Log.d(LOG_TAG, "GPS provider - requestLocationUpdates");
        checkEnable();
    }

    @Override
    protected void onPause() {
        super.onPause();
    Log.d(LOG_TAG, "on Pause - start");
        locationManager.removeUpdates(locationListener);
    Log.d(LOG_TAG, "locationManager.removeUpdates(locationListener)");
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
    Log.d(LOG_TAG, "onLocationChanged");
            showLocation(location);
    Log.d(LOG_TAG, "showLocation");
        }

        @Override
        public void onProviderDisabled(String provider) {
    Log.d(LOG_TAG, "onProviderDisable");
    Log.d(LOG_TAG, "Provider - "+provider);
            checkEnable();
        }

        @Override
        public void onProviderEnabled(String provider) {
    Log.d(LOG_TAG, "onProviderEnable");
    Log.d(LOG_TAG, "Provider - " + provider);
            checkEnable();
            showLocation(locationManager.getLastKnownLocation(provider));
    Log.d(LOG_TAG, "showLocation(locationManager.getLastKnownLocation(provider))");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
    Log.d(LOG_TAG, "onStatusChanged");
    Log.d(LOG_TAG, "Provider - " + provider+"/status - "+String.valueOf(status));
            if (provider.equals(LocationManager.PASSIVE_PROVIDER)){
    Log.d(LOG_TAG, "provider.equals(LocationManager.PASSIVE_PROVIDER)");
            }else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
    Log.d(LOG_TAG, "provider.equals(LocationManager.Network_PROVIDER)");
            }else if (provider.equals(LocationManager.GPS_PROVIDER)){
    Log.d(LOG_TAG, "provider.equals(LocationManager.GPS_PROVIDER)");
            }
        }
    };

    private void showLocation(Location location) {
    Log.d(LOG_TAG, "---Show location start---");
        if (location == null) {
    Log.d(LOG_TAG, "Location = null");
            return;
        }
        if (location.getProvider().equals(LocationManager.PASSIVE_PROVIDER)) {
    Log.d(LOG_TAG, "location.getProvider().equals(LocationManager.PASSIVE_PROVIDER)");
            loc_latit = location.getLatitude();
    Log.d(LOG_TAG, "loc_latit = "+location.getLatitude());
            loc_longt = location.getLongitude();
    Log.d(LOG_TAG, "loc_longt = "+ location.getLongitude());
            goNextIntent(loc_latit, loc_longt);
    Log.d(LOG_TAG, "goNextIntent");
        }
        if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
    Log.d(LOG_TAG, "location.getProvider().equals(LocationManager.NETWORK_PROVIDER)");
            loc_latit = location.getLatitude();
    Log.d(LOG_TAG, "loc_latit = "+location.getLatitude());
            loc_longt = location.getLongitude();
    Log.d(LOG_TAG, "loc_longt = "+ location.getLongitude());
            goNextIntent(loc_latit, loc_longt);
    Log.d(LOG_TAG, "goNextIntent");
        }
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
    Log.d(LOG_TAG, "location.getProvider().equals(LocationManager.GPS_PROVIDER)");
            loc_latit = location.getLatitude();
    Log.d(LOG_TAG, "loc_latit = "+location.getLatitude());
            loc_longt = location.getLongitude();
    Log.d(LOG_TAG, "loc_longt = "+ location.getLongitude());
            goNextIntent(loc_latit, loc_longt);
    Log.d(LOG_TAG, "goNextIntent");
        }
    }

    private void goNextIntent(double lat, double longt){
    Log.d(LOG_TAG, "--- Start goNextIntent---");
        if ((loc_longt!=0.0) && (loc_longt!=0.0)) {
        Log.d(LOG_TAG, loc_longt + "!=0.0) && (" + loc_longt + "!=0.0)");
            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
            intent.putExtra("1",String.valueOf(lat));
        Log.d(LOG_TAG, "intent.putExtra( 1," + String.valueOf(lat));
            intent.putExtra("2", String.valueOf(longt));
        Log.d(LOG_TAG, "intent.putExtra( 2," + String.valueOf(longt));
            startActivity(intent);
        Log.d(LOG_TAG, "go To Second Activity");
            finish();
        Log.d(LOG_TAG, "finish FirstActivity");
        }
    }

    private void checkEnable(){
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)==false
                && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)==false){
            info.setVisibility(View.INVISIBLE);
            set_inf.setVisibility(View.VISIBLE);
            openSettings.setVisibility(View.VISIBLE);
            myPB.setVisibility(View.INVISIBLE);
        } else {
            info.setVisibility(View.VISIBLE);
            set_inf.setVisibility(View.INVISIBLE);
            openSettings.setVisibility(View.INVISIBLE);
            myPB.setVisibility(View.VISIBLE);
        }
    }

    public void onClickLocSett(View view){
        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

}
