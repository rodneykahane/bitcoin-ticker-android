package com.londonappbrewery.bitcointicker;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

public class BitcoinDataModel {

    //member variables
    private long tmpPrice;
    private long tmpRound;
    private String mPrice;


    //PriceDataModel from a JSON
    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static BitcoinDataModel fromJSON(JSONObject jsonObject){

        //using this to parse the currency format
        String countryObjectString= jsonObject.toString();
        String country = countryObjectString.substring(2,8);


        try {

            Log.d("Bitcoin-Ticker","BitcoinDataModel- the value from string chopping is "+country);

            BitcoinDataModel bitcoinData = new BitcoinDataModel();


            bitcoinData.tmpPrice = jsonObject.getJSONObject(country).getLong("last");
            Log.d("Bitcoin-ticker"," BitCoinDataModel - the value of BTCcountry is "+"BTC"+country);

            //bitcoinData.tmpRound = Math.round(bitcoinData.tmpPrice);
            bitcoinData.mPrice = Double.toString(bitcoinData.tmpPrice);

            Log.d("Bitcoin-Ticker","BitcoinDataModel - the value of json last is "+bitcoinData.mPrice);

            return bitcoinData;
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }//end try/catch

    }//end BitcoinDataModel


    public String getPrice() {
        return mPrice;
    }

}//end class
