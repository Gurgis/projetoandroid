package com.example.findinglogs.viewmodel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.findinglogs.model.model.Weather;
import com.example.findinglogs.model.repo.Repository;
import com.example.findinglogs.model.repo.remote.api.WeatherCallback;
import com.example.findinglogs.model.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private static final int FETCH_INTERVAL = 120_000;
    private final Repository mRepository;
    private final MutableLiveData<List<Weather>> _weatherList = new MutableLiveData<>(new ArrayList<>());
    private final LiveData<List<Weather>> weatherList = _weatherList;
    private final MutableLiveData<Integer> refreshTrigger = new MutableLiveData<>(0); // Novo trigger
//atualização: agora a activity apenas "pede" uma atualização pelo refreshdata
    //viewmodel decide como fazer issoi
        //agora está mais no padrão mvvm

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable fetchRunnable = this::fetchAllForecasts;

    public MainViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        startFetching();

        // Observa mudanças no trigger para atualizações manuais
        refreshTrigger.observeForever(trigger -> {
            if (trigger > 0) { // Evita execução no init
                fetchAllForecasts();
            }
        });
    }

    public LiveData<List<Weather>> getWeatherList() {
        return weatherList;
    }

    private void startFetching() {
        fetchAllForecasts();
        handler.postDelayed(fetchRunnable, FETCH_INTERVAL);
    }

    private void fetchAllForecasts() {
        if (Logger.ISLOGABLE) Logger.d(TAG, "fetchAllForecasts()");
        HashMap<String, String> localizations = mRepository.getLocalizations();
        List<Weather> updatedList = new ArrayList<>();

        for (String latlon : localizations.values()) {
            mRepository.retrieveForecast(latlon, new WeatherCallback() {
                @Override
                public void onSuccess(Weather result) {
                    updatedList.add(result);
                    if (updatedList.size() == localizations.size()) {
                        _weatherList.postValue(updatedList);
                        handler.postDelayed(fetchRunnable, FETCH_INTERVAL);
                    }
                }

                @Override
                public void onFailure(String error) {
                    handler.postDelayed(fetchRunnable, FETCH_INTERVAL);
                }
            });
        }
    }

    // metodo para a atualização manual

    public void refreshData() {
        refreshTrigger.setValue(refreshTrigger.getValue() + 1);
    }

    @Override
    protected void onCleared() {
        handler.removeCallbacks(fetchRunnable);

        // Para observers usando observeForever, eu posso fazer a remoção assim:
        refreshTrigger.removeObserver(observer -> {}); // Remove todos os observers

        super.onCleared();
    }
}