package com.rja_belen.uber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnDriver, btnClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDriver = findViewById(R.id.btnDriver);
        btnClient = findViewById(R.id.btnClient);

        btnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DriverLoginAct.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CustomerLoginAct.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}