package com.myapp.stockapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button_stocks;
    private Button button_crypto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_crypto = findViewById(R.id.crypto);
        button_stocks = findViewById(R.id.stocks);

        button_stocks.setOnClickListener(v -> openStocks());

        button_crypto.setOnClickListener(v -> openCrypto());
    }

    private void openCrypto() {
        Intent intent = new Intent(this, Crypto.class);
        startActivity(intent);
    }

    private void openStocks() {
        Intent intent = new Intent(this, Stocks.class);
        startActivity(intent);
    }
}