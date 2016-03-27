package com.artmakdeveloper.whattoweartoday.WearUtils;

import com.artmakdeveloper.whattoweartoday.DataForTest.TestData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyWear implements IMyWear {

    TestData td = new TestData();


    public String giveItem(int minT, int maxT, String nameItem) throws JSONException {

        String tmp = td.getWearData();
        String res = null;
        int t_min = 0, t_max = 0;

        JSONObject jsonRootObject = null;
        jsonRootObject = new JSONObject(tmp);
        JSONArray headItemJsonArray = null;
        headItemJsonArray = jsonRootObject.getJSONArray(nameItem);
        for (int i = 0; i < headItemJsonArray.length(); i++) {
            JSONObject jsonObject = null;
            jsonObject = headItemJsonArray.getJSONObject(i);
            t_min = jsonObject.getInt("t_min");
            t_max = jsonObject.getInt("t_max");

            int t_ser = (minT + maxT) / 2;
            if ((t_min <= t_ser) && (t_ser <= t_max)) {
                res = jsonObject.optString("text").toString();
            }
        }

        return res;
    }
}
