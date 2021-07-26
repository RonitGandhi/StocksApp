package com.myapp.stockapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Stocks extends AppCompatActivity {

    private final  String endpoint = "BSE";
    private EditText name_stock;
    private Button go_btn;
    private TextView symbol_text, open_txt, high_txt, low_txt, end_txt,volume_txt, last_trading,prev_text,change_rs, change_pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);

        name_stock = findViewById(R.id.get_stock);
        go_btn = findViewById(R.id.go);

        symbol_text = findViewById(R.id.symbol);
        open_txt = findViewById(R.id.open);
        high_txt = findViewById(R.id.high);
        low_txt = findViewById(R.id.low);
        end_txt = findViewById(R.id.end_price);
        volume_txt = findViewById(R.id.volume);
        last_trading = findViewById(R.id.trading_day);
        prev_text = findViewById(R.id.prev_close);
        change_rs = findViewById(R.id.change_rupees);
        change_pr = findViewById(R.id.change_percent);

        go_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void getData() {


        String stock = name_stock.getText().toString().toUpperCase(Locale.ENGLISH);
        String URL = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+stock+"."+endpoint+"&apikey=YOUR_API_KEY";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response.getJSONObject("Global Quote");
                    String symbol = jsonObject.getString("01. symbol");
                    String open_price = jsonObject.getString("02. open").substring(0,6);
                    String high_price = jsonObject.getString("03. high").substring(0,6);
                    String low_price = jsonObject.getString("04. low").substring(0,6);
                    String price_end = jsonObject.getString("05. price").substring(0,6);
                    String volume = jsonObject.getString("06. volume");
                    String last_trading_day = jsonObject.getString("07. latest trading day");
                    String prev_close = jsonObject.getString("08. previous close").substring(0,6);
                    String change_rupees = jsonObject.getString("09. change");
                    String change_percent = jsonObject.getString("10. change percent");

                    symbol_text.setText("Symbol: "+symbol);
                    open_txt.setText("Open price : Rs " +open_price);
                    high_txt.setText("High price : Rs " +high_price);
                    low_txt.setText("Low price : Rs " +low_price);
                    end_txt.setText("End price : Rs " +price_end);
                    volume_txt.setText("Trading volumes : "+volume);
                    last_trading.setText("Last trading day : "+last_trading_day);
                    prev_text.setText("Previous closing price (Day before yesterday) : Rs " +prev_close);
                    change_rs.setText("Change in Rupees (Rs) :  " +change_rupees);
                    change_pr.setText("Change in percent : " +change_percent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Stocks.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }
}