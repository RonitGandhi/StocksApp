package com.myapp.stockapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
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

public class Crypto extends AppCompatActivity {

    private EditText crypto_text;
    private Button go;
    private TextView currency_txt, exchng_txt, last_txt, bid_txt, ask_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto);

        crypto_text = findViewById(R.id.get_crypto);
        go = findViewById(R.id.go);

        currency_txt =findViewById(R.id.name_currency);
        exchng_txt = findViewById(R.id.exchange_rate);
        last_txt = findViewById(R.id.last_refreshed);
        bid_txt = findViewById(R.id.bid);
        ask_txt = findViewById(R.id.ask);

        go.setOnClickListener(v -> getDataCrypto());
    }

    private void getDataCrypto() {
        String crypto = crypto_text.getText().toString();
        String URL = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency="+crypto+"&to_currency=INR&apikey=YOUR_API_KEY";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject2 = response.getJSONObject("Realtime Currency Exchange Rate");

                    String name_curr = jsonObject2.getString("2. From_Currency Name");
                    String exchange_rate = jsonObject2.getString("5. Exchange Rate").substring(0,10);
                    String last_refreshed = jsonObject2.getString("6. Last Refreshed");
                    String bid_price = jsonObject2.getString("8. Bid Price").substring(0,10);
                    String ask_price = jsonObject2.getString("9. Ask Price").substring(0,10);

                    currency_txt.setText("Name: " +name_curr);
                    currency_txt.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    exchng_txt.setText("Exchange Rate in rupees (Rs) : \n Rs "+exchange_rate);
                    exchng_txt.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    last_txt.setText("Last refreshed: " +last_refreshed);
                    last_txt.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    bid_txt.setText("Bid Price in Rs : Rs " +bid_price);
                    bid_txt.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    ask_txt.setText("Ask Price in Rs : Rs " +ask_price);
                    ask_txt.setBackgroundColor(getResources().getColor(R.color.teal_700));




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Crypto.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }
}