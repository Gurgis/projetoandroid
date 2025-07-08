package com.example.findinglogs.view;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findinglogs.R;
import com.example.findinglogs.broadcast.WifiStateReceiver;
import com.example.findinglogs.model.model.Weather;
import com.example.findinglogs.view.recyclerview.adapter.WeatherListAdapter;
import com.example.findinglogs.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

//iMPORTS PARA O BROADCAST RECEIVER PARA WIFI
import android.net.wifi.WifiManager;

public class MainActivity extends AppCompatActivity {

    private WeatherListAdapter adapter;
    private final List<Weather> weathers = new ArrayList<>();
    private MainViewModel viewModel;

    //Declarando variaveis para a verificação do wifi
    private WifiStateReceiver wifiReceiver;
     private BroadcastReceiver updateReceiver;


    public static boolean isActive = false;

    @Override
    protected void onStart(){
        super.onStart();
        isActive=true;
        wifiReceiver = new WifiStateReceiver();
        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter);
    }
    @Override
    protected void onStop(){
        super.onStop();
        isActive=false;
        if(wifiReceiver!=null){
            unregisterReceiver(wifiReceiver);
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();

        if(updateReceiver!=null){
            unregisterReceiver(updateReceiver);
        }
    }
   // private String getWifiStateName(int state) {
      //  switch (state) {
           // case WifiManager.WIFI_STATE_DISABLED:
            //    return "Desligado";
           // case WifiManager.WIFI_STATE_DISABLING:
            //    return "Desligando";
           // case WifiManager.WIFI_STATE_ENABLED:
             //   return "Ligado";
            //case WifiManager.WIFI_STATE_ENABLING:
             //   return "Ligando";
            //case WifiManager.WIFI_STATE_UNKNOWN:
           // default:
             //   return "Desconhecido";
       // }
   // }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //verifica o estado atual do wifi
       WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);





        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_weather);
        FloatingActionButton fetchButton = findViewById(R.id.fetchButton);

        adapter = new WeatherListAdapter(this, weathers);
        recyclerView.setAdapter(adapter);

        // Observador da lista de dados
        viewModel.getWeatherList().observe(this, weathers -> {
            adapter.updateWeathers(weathers);
        });

        // Botão de atualização (agora chama refreshData)
        fetchButton.setOnClickListener(v -> {
            viewModel.refreshData();
            Toast.makeText(MainActivity.this, "Atualizando...", Toast.LENGTH_SHORT).show();
        });
    }
}