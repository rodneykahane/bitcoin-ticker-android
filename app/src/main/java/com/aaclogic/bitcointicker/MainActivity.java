package com.aaclogic.bitcointicker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/short";

    final int REQUEST_CODE = 123;

    // Member Variables:
    TextView mPriceTextView;
    String mCurrencyType="AUD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = findViewById(R.id.priceLabel);
        Spinner spinner = findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner
            spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    Object item = parent.getItemAtPosition(position);
                    if(item !=null){
                        mCurrencyType = item.toString();
                        setCurrencyType(mCurrencyType);
                        Log.d("Bitcoin-Ticker","OnItemSelected - the value of the getter is "+getCurrencyType());
                        getCoinPrice();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

    }//end onCreate



    @Override
    protected void onResume(){
        super.onResume();
        Log.d("Bitcoin-ticker","onResume() called");

        getCoinPrice();


    }//end onResume

    private void getCoinPrice(){
        //TODO need to replace hardcoded USD to point at what the spinner comes back with
        String fiat = mCurrencyType;
        String crypto = "BTC";

        Log.d("Bitcoin-Ticker","getCoinPrice() - the value of the 'fiat' variable used in url is " + fiat);

        RequestParams params = new RequestParams();
        params.put("crypto", crypto);
        params.put("fiat", fiat);
        letsDoSomeNetworking(params);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE);
            return;
        }



    }//end getCoinPrice

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,grantResults);

        if (requestCode == REQUEST_CODE) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Bitcoin-Ticker","onRequestPermissionsResult(): PermissionGranted!");
                getCoinPrice();
            } else {
                Log.d("Bitcoin-Ticker","Permission denied =(");
            }
        }
    }//end onRequestPermissionsResult



    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(RequestParams params) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL, params, new JsonHttpResponseHandler() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("Bitcoin-Ticker", "letsDoSomeNetworking() JSON: " + response.toString());
                BitcoinDataModel bitcoinData = BitcoinDataModel.fromJSON(response);
                Log.d("Bitcoin-Ticker:","letsDoSomeNetowrking() - the value of mCurrencyType is "+mCurrencyType);
                updateUI(bitcoinData);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("Bitcoin-Ticker", "Request fail! Status code: " + statusCode);
                Log.d("Bitcoin-Ticker", "Fail response: " + response);
                Log.e("ERROR", e.toString());
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }//end letsDoSomeNetworking

    private void updateUI(BitcoinDataModel bitcoin){
        Log.d("Bitcoin-Ticker","inside of updateUI now");
        String formatPrice=bitcoin.getPrice();
        Log.d("Bitcoin-Ticker","value of formatPrice is "+formatPrice);
        String formattedPrice;

       // NumberFormat formatter = NumberFormat.getCurrencyInstance();
        //formattedPrice=formatter.format(formatPrice);
        setCurrencyType(mCurrencyType);
        Log.d("Bitcoin-Ticker:","updateUI() the value of mCurrencyType is "+mCurrencyType);
        mPriceTextView.setText(formatPrice);

    }//end updateUI


    public String getCurrencyType() {


        return mCurrencyType;
    }

    public void setCurrencyType(String currencyType) {

        mCurrencyType = currencyType;
    }
}//end class
