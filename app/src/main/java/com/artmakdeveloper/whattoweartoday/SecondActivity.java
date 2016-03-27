package com.artmakdeveloper.whattoweartoday;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.artmakdeveloper.whattoweartoday.DBUtils.DBHelper;
import com.artmakdeveloper.whattoweartoday.MyDateUtils.MyDate;
import com.artmakdeveloper.whattoweartoday.WeatherUtils.WeatherHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class SecondActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";
    double longt,lat;
    String res;
    WeatherHttpClient weatherHttpClient = new WeatherHttpClient();
    MyDate nowDate = new MyDate();
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    Log.d(LOG_TAG, "- - - start Second Activity - - -");
        String slat = getIntent().getStringExtra("1");
    Log.d(LOG_TAG, "Slat="+slat);
        lat = Double.parseDouble(slat);
        String slon = getIntent().getStringExtra("2");
    Log.d(LOG_TAG, "Slon="+slon);
        longt = Double.parseDouble(slon);
//        TextView textView = (TextView) findViewById(R.id.tv_link);
//        textView.setText(weatherHttpClient.getLink(lat,longt));

        new MyTask().execute();

    }

    private class MyTask  extends AsyncTask<Void, Void, Void> {
        URL textUrl;

        @Override
        protected Void doInBackground(Void... params) {
    Log.d(LOG_TAG, "- - - Start My Task  do in background- - -");
            try {
                textUrl = new URL(weatherHttpClient.getLink(lat,longt));
                Log.d(LOG_TAG, "TextUrl="+textUrl.toString());
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(textUrl.openStream()));
                String stringBuffer;
                String stringText =  " ";
                while ((stringBuffer=bufferedReader.readLine())!=null){
                    stringText+=stringBuffer;
                }
                bufferedReader.close();
                res = stringText;
            Log.d(LOG_TAG, "Res="+res);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        Log.d(LOG_TAG, "- - - Start my asymctask - - - Post execute - - -");
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            //weatherHttpClient.setAnswerServer(res);

            dbHelper = new DBHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            dbHelper.addLine(db,nowDate.getNowData(), res);
        Log.d(LOG_TAG, "Set DB line=" + res);
            //Toast.makeText(getApplicationContext(),"Line add in DB",Toast.LENGTH_LONG).show();

            startActivity(intent);
        Log.d(LOG_TAG, "Go to Main Activity");
            finish();
        Log.d(LOG_TAG, "- - - Finish Second Activity - - - ");
        }
    }
}
