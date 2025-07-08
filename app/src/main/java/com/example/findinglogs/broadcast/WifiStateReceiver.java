package com.example.findinglogs.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.example.findinglogs.view.MainActivity;

public class WifiStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        if(intent == null || intent.getAction() == null) return;

        //VERIFICA SE A INTENT É DO WIFI
        if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            //PEGA NOVO ESTADO DO WIFI
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);

            //DETERMINA A MENSAGEM DO TOAST
            String statusText;
            switch (wifiState) {
                case WifiManager.WIFI_STATE_ENABLED:
                    statusText = "Wi-Fi LIGADO";
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    statusText = "Wi-Fi DESLIGADO";
                    break;
                default:

                    return;
            }

            Toast.makeText(context, statusText, Toast.LENGTH_SHORT).show();

            //atualiza o status
            if(MainActivity.isActive){
                Intent updateIntent = new Intent("UPDATE_WIFI_STATUS");
                updateIntent.putExtra("status", statusText);
                context.sendBroadcast(updateIntent);
            }
        }
    }
}