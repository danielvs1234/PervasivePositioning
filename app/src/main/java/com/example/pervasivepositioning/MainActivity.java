package com.example.pervasivepositioning;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    WifiSignalDataCollector wifiSignal;

    Button signalButton;
    Button scanButton;
    TextView dataInputView;
    TextView dataTextView;
    TextView distTextView;
    List<String> ssids = new ArrayList<>();
    Map<String, Map<String, Integer>> training = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int PERMISSION_REQUEST_CODE = 69;

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            ssids.add("westh");
            ssids.add("cablebox-4d68");
            ssids.add("k");
        }




        wifiSignal = new WifiSignalDataCollector(getApplicationContext());

        signalButton = findViewById(R.id.signalButton);
        scanButton = findViewById(R.id.scanButton);

        dataTextView = findViewById(R.id.dataTextView);
        dataTextView.setMovementMethod(new ScrollingMovementMethod());
        dataTextView.setText("");

        distTextView = findViewById(R.id.distanceTextView);
        distTextView.setMovementMethod(new ScrollingMovementMethod());
        distTextView.setText("");

        dataInputView = findViewById(R.id.dataInputText);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiSignal.scan();
            }
        });

        signalButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                List<ScanResult> result = wifiSignal.calculateSignalLevel(4);
                for(int i = 0; i<result.size(); i++){
                    String gridPos = dataInputView.getText().toString();
                    Map<String, Integer> temp = new HashMap<>();
                    for(int j = 0; j <ssids.size();j++){
                        if(result.get(i).SSID.equalsIgnoreCase(ssids.get(j)))
                            temp.put(result.get(i).SSID, result.get(i).frequency);
                    }
                    training.put(gridPos, temp);
                }
            }
        });
    }
}
