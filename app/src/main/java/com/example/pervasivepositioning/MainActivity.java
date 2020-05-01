package com.example.pervasivepositioning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    WifiSignalDataCollector wifiSignal;

    Button signalButton;
    TextView dataTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiSignal = new WifiSignalDataCollector(getApplicationContext());

        signalButton = findViewById(R.id.signalButton);
        dataTextView = findViewById(R.id.dataTextView);

        signalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int level =  wifiSignal.calculateSignalLevel(5);
               dataTextView.append("" + level + "\n");
            }
        });
    }

}
