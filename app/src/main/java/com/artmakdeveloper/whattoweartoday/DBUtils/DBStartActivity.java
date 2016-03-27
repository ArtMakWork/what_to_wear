package com.artmakdeveloper.whattoweartoday.DBUtils;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.artmakdeveloper.whattoweartoday.MyDateUtils.MyDate;
import com.artmakdeveloper.whattoweartoday.R;

public class DBStartActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnRead, btnClear ,btnLast;
    EditText et_Date, et_String;
    MyDate nowDate = new MyDate();
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_db);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btn_show);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(this);

        btnLast = (Button) findViewById(R.id.btn_last);
        btnLast.setOnClickListener(this);

        et_Date = (EditText) findViewById(R.id.et_date);
        et_String = (EditText) findViewById(R.id.et_string);

        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();



    }

    @Override
    public void onClick(View v) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (v.getId()) {
            case R.id.btn_add:
                dbHelper.addLine(db, et_Date.getText().toString(), et_String.getText().toString());
                break;

            case R.id.btn_show:
                dbHelper.showFullTable(db);
                break;

            case R.id.btn_clear:
                dbHelper.clearAllTable(db);
                break;

            case R.id.btn_last:
                String last_date_in_BD = dbHelper.getLastLine(db);
                if (last_date_in_BD.equals(nowDate.getNowData())){
                    Toast.makeText(this,"Sovpadaet",Toast.LENGTH_SHORT).show();
                    Toast.makeText(this,dbHelper.getLastText(db),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"HET",Toast.LENGTH_SHORT).show();
                }
                break;
        }
            dbHelper.close();
        }

}
