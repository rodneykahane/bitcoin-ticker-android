package com.londonappbrewery.bitcointicker;

import org.json.JSONException;
import org.json.JSONObject;

public class BitcoinDataModel {

    //member variables
    private String mPrice;

    //PriceDataModel from a JSON
    public static BitcoinDataModel fromJSON(JSONObject jsonObject){

        try {
            BitcoinDataModel bitcoinData = new BitcoinDataModel();

            bitcoinData.mPrice = jsonObject.getString("last");
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }//end try/catch



    }//end BitcoinDataModel


}
