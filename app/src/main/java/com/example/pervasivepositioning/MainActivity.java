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

import java.util.List;

public class MainActivity extends AppCompatActivity {

    WifiSignalDataCollector wifiSignal;

    Button signalButton;
    Button scanButton;
    TextView dataTextView;
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
        }




        wifiSignal = new WifiSignalDataCollector(getApplicationContext());

        signalButton = findViewById(R.id.signalButton);
        scanButton = findViewById(R.id.scanButton);

        dataTextView = findViewById(R.id.dataTextView);
        dataTextView.setMovementMethod(new ScrollingMovementMethod());
        dataTextView.setText("");

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiSignal.scan();
            }
        });

        signalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ScanResult> result = wifiSignal.calculateSignalLevel(4);
                if(result.size() > 0) {
                    for(ScanResult r : result){
                        if(r.SSID.equals("Westh")){
                            dataTextView.append("" + r.level + "\n");
                        }
                    }

               }
            }
        });
    }

}
