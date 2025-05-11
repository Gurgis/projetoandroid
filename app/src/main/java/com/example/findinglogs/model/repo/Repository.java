package com.example.findinglogs.model.repo;


import android.app.Application;

import com.example.findinglogs.model.repo.local.SharedPrefManager;
import com.example.findinglogs.model.repo.remote.WeatherManager;
import com.example.findinglogs.model.repo.remote.api.WeatherCallback;
import com.example.findinglogs.model.util.Logger;

import java.util.HashMap;

public class Repository {
    private static final String TAG = Repository.class.getSimpleName();

    private final WeatherManager weatherManager;
    private final SharedPrefManager sharedPrefManagerManager;

    public Repository(Application application) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "Repository()");
        weatherManager = new WeatherManager();
        sharedPrefManagerManager = SharedPrefManager.getInstance(application);
    }

    public void retrieveForecast(String latLon, WeatherCallback callback) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "retrieveForecast for:" + latLon);
        weatherManager.retrieveForecast(latLon, callback);
    }

    public void saveString(String key, String value) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "saveString()");
        sharedPrefManagerManager.writeString(key, value);
    }

    public String readString(String key) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "readString()");
        return sharedPrefManagerManager.readString(key);
    }

    public HashMap<String, String> getLocalizations() {
        HashMap<String, String> localizations = new HashMap<>();
        localizations.put("1", "-8.05428,-34.8813");// recife
        localizations.put("2", "-3.1019,-60.025");// manaus
        localizations.put("3", "-22.9068,-43.1729");// rio de janeiro
        localizations.put("4", "-1.4558,-48.4902");// belém


        return localizations;
    }
}