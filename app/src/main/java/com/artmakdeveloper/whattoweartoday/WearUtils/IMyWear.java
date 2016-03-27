package com.artmakdeveloper.whattoweartoday.WearUtils;

import org.json.JSONException;

/**
 * Created by HOME on 03.03.2016.
 */
public interface IMyWear {
    String giveItem(int minT, int maxT, String nameItem)throws JSONException;
}
