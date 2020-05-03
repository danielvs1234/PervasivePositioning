package com.example.pervasivepositioning;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class WifiSignalDataCollector {

    int signalLevel;
    WifiManager wifiManager;
    WifiInfo wifiInfo;
    Context context;

    @SuppressLint("ServiceCast")
    public WifiSignalDataCollector(Context context){
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        this.wifiInfo = wifiManager.getConnectionInfo();
        this.context = context;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public List<ScanResult> calculateSignalLevel(int levels) {
        wifiManager.startScan();
        List<ScanResult> result = wifiManager.getScanResults();
        for(ScanResult r : result){
            Log.d("Scan", "ScanResult level: " + r.SSID + " "+ r.level);
        }

        return result;
    }

    public void scan(){
        ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).startScan();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int getDistance(int freq, int signalLevel){
        int distance = 0;
        double frequency = (double) freq;
       // distance = 10.0 ^ ((27.55 - (20.0 * Math.log10(frequency) + signalLevel)/20.0));

        return distance;
    }
}