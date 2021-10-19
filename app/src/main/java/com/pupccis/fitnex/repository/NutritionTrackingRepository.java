package com.pupccis.fitnex.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.pupccis.fitnex.model.FoodData;
import com.pupccis.fitnex.utilities.Constants.NutritionTrackingConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NutritionTrackingRepository {
    public MutableLiveData<List<FoodData>> getFoodResults(RequestQueue queue, String queryFood){
        MutableLiveData<List<FoodData>> result = new MutableLiveData<>();
        List<FoodData> foodDataList = new ArrayList<>();
        String url ="https://api.nal.usda.gov/fdc/v1/foods/search?query="+queryFood+"&pageSize=10&api_key=JOrlLA8RuHz2iQtAuveGa8jcxcqVipqpHvFzT5LX";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("foods");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject foodData = jsonArray.getJSONObject(i);
                                FoodData.Builder builder = new FoodData.Builder()
                                        .fcdID(foodData.getInt("fdcId"))
                                        .name(foodData.getString("description"));

                                JSONArray foodNutrients = foodData.getJSONArray("foodNutrients");
                                for(int j=0; j<foodNutrients.length(); j++){
                                    JSONObject foodNutrient = foodNutrients.getJSONObject(j);
                                    if(foodNutrient.getInt("nutrientId") == NutritionTrackingConstants.NutrientID.ENERGY)
                                        builder.calories(foodNutrient.getDouble("value"));

                                }
                                foodDataList.add(builder.build());
                            }
                            result.postValue(foodDataList);

                        }
                        catch (JSONException e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
        return result;
    }

    public MutableLiveData<FoodData> getFoodInfo(RequestQueue queue, FoodData foodData) {
        MutableLiveData<FoodData> liveFoodInfo = new MutableLiveData<>();
        int fcdID = foodData.getFcdID();
        String url = "https://api.nal.usda.gov/fdc/v1/food/"+fcdID+"?api_key=JOrlLA8RuHz2iQtAuveGa8jcxcqVipqpHvFzT5LX";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            FoodData foodDataInfo = null;
                            if(jsonObject.getString("dataType").equals("Branded")){
                                JSONObject nutrients = jsonObject.getJSONObject("labelNutrients");
                                foodDataInfo = new FoodData.Builder()
                                        .name(jsonObject.getString("description"))
                                        .fcdID(jsonObject.getInt("fdcId"))
                                        .calories(foodData.getCalories())
                                        .servingSize(jsonObject.getDouble("servingSize"))
                                        .protein(nutrients.getJSONObject("protein").getDouble("value"))
                                        .fats(nutrients.getJSONObject("fat").getDouble("value"))
                                        .carbs(nutrients.getJSONObject("carbohydrates").getDouble("value"))
                                        .build();
                            }

                            liveFoodInfo.postValue(foodDataInfo);
                        }
                        catch (JSONException e){
                            Log.d("JSON Error", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
        return liveFoodInfo;
    }
}
