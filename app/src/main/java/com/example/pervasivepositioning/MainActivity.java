package com.example.pervasivepositioning;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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
    Button fileContent;
    Button deleteButton;
    Button checkPosButton;
    TextView dataInputView;
    TextView dataTextView;
    TextView posTextView;
    List<String> ssids = new ArrayList<>();
    Map<String, Map<String, Integer>> training = new HashMap<>();
    StorageHandler storageHandler;
    private ClassifierClass classifierClass;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        classifierClass = new ClassifierClass(this);

        ssids.add("Westh");
        ssids.add("DIRECT-EO pass-PHILIPS TV");
        ssids.add("ismart");
        storageHandler = new StorageHandler(getApplicationContext());


        int PERMISSION_REQUEST_CODE = 69;

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);

        }




        wifiSignal = new WifiSignalDataCollector(getApplicationContext());

        signalButton = findViewById(R.id.signalButton);
        scanButton = findViewById(R.id.scanButton);
        fileContent = findViewById(R.id.fileContent);
        deleteButton = findViewById(R.id.deleteButton);
        checkPosButton = findViewById(R.id.checkPositionButton);

        dataTextView = findViewById(R.id.dataTextView);
        dataTextView.setMovementMethod(new ScrollingMovementMethod());
        dataTextView.setText("");

        posTextView = findViewById(R.id.positionTextView);
        posTextView.setMovementMethod(new ScrollingMovementMethod());
        posTextView.setText("");

        dataInputView = findViewById(R.id.dataInputText);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiSignal.scan();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApplicationContext().deleteFile("map.dat");
            }
        });

        fileContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataTextView.setText("");
                dataTextView.setText(storageHandler.read());
            }
        });

        //Add Training point to file.
        signalButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                List<ScanResult> result = wifiSignal.calculateSignalLevel(4);
                String gridPos = dataInputView.getText().toString();
                StringBuilder string = new StringBuilder();
                string.append(gridPos + ";");
                for(int i = 0; i<result.size(); i++){
                    Map<String, Integer> temp = new HashMap<>();
                    for(String j : ssids){
                        if(result.get(i).SSID.equals(j)) {
                            temp.put(result.get(i).SSID, result.get(i).level);
                            Log.d("ClickingMahBaby", "result: " + result.get(i).SSID + " " + result.get(i).level);
                            string.append(result.get(i).level+",");
                        }
                    }
                }
                    writeString(cutChar(string.toString()) + "\n");

            }
        });

        checkPosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posTextView.setText("");
                String currentPositionString = "";
                posTextView.setText("Current location is: " + currentPositionString);
            }

        });

    }


    public String cutChar(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    private void writeString(String string){
        if(storageHandler.checkIfFileExists()){
            String temp = storageHandler.read();
            String newString = temp + string;
            storageHandler.write(newString);
        }else{
            storageHandler.write(string);
        }

    }
}
