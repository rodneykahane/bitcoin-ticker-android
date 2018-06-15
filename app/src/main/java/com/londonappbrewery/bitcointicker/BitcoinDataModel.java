package com.londonappbrewery.bitcointicker;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

public class BitcoinDataModel {

    //member variables
    private long tmpPrice;
    private long tmpRound;
    private String mPrice;


    //PriceDataModel from a JSON
    public static BitcoinDataModel fromJSON(JSONObject jsonObject){
        MainActivity tc = new MainActivity();
        String country = tc.getCountry();

        Log.d("Bitcoin-Ticker","the value from currency getter is "+country);
        try {
            BitcoinDataModel bitcoinData = new BitcoinDataModel();



            //TODO need to change BTCUSD to something like BTC+StringVariable
            bitcoinData.tmpPrice = jsonObject.getJSONObject("BTC"+country).getLong("last");
            Log.d("bitcoin-ticker","the value of BTC+country is "+"BTC"+country);

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
