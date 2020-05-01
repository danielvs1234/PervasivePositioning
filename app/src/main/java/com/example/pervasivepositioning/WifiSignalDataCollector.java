package com.example.pervasivepositioning;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class WifiSignalDataCollector {

    int signalLevel;
    WifiManager wifiManager;
    WifiInfo wifiInfo;

    @SuppressLint("ServiceCast")
    public WifiSignalDataCollector(Context context){
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        this.wifiInfo = wifiManager.getConnectionInfo();

    }

    public int calculateSignalLevel(int levels) {
        return signalLevel = wifiManager.calculateSignalLevel(wifiInfo.getRssi(), levels);
    }
}
