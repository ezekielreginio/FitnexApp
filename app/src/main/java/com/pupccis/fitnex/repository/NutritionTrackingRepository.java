package com.pupccis.fitnex.repository;

import static com.pupccis.fitnex.api.DateFormatter.getCurrentDate;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pupccis.fitnex.activities.nutritiontracking.enums.Meals;
import com.pupccis.fitnex.model.FoodData;
import com.pupccis.fitnex.utilities.Constants.UserConstants;
import com.pupccis.fitnex.utilities.Constants.nutritionTrackingConstants.NutritionTrackingConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NutritionTrackingRepository {
    //Static Attributes
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                                JSONArray foodNutrients = foodData.getJSONArray("foodNutrients");
                                String datatype = foodData.getString("dataType");

                                if(datatype.equals("Survey (FNDDS)") || datatype.equals("Branded")){
                                    FoodData.Builder builder = new FoodData.Builder()
                                            .fcdID(foodData.getInt("fdcId"))
                                            .name(foodData.getString("description"));

                                    for(int j=0; j<foodNutrients.length(); j++){
                                        JSONObject foodNutrient = foodNutrients.getJSONObject(j);
                                        switch (foodNutrient.getInt("nutrientId")){
                                            case NutritionTrackingConstants.NutrientID.ENERGY:
                                                builder.calories(foodNutrient.getDouble("value"));
                                                break;

                                            case NutritionTrackingConstants.NutrientID.PROTEIN:
                                                builder.protein(foodNutrient.getDouble("value"));
                                                break;

                                            case NutritionTrackingConstants.NutrientID.CARBS:
                                                builder.carbs(foodNutrient.getDouble("value"));
                                                break;

                                            case NutritionTrackingConstants.NutrientID.FAT:
                                                builder.fats(foodNutrient.getDouble("value"));
                                                break;
                                        }
                                    }
                                    foodDataList.add(builder.build());
                                }
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 10, 1f));
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
                                        .servingAmount(jsonObject.getInt("servingSize"))
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

    public MutableLiveData<JSONArray> getServingInfo(RequestQueue queue, int fcdID) {
        MutableLiveData<Integer> servingInfo = new MutableLiveData<>();
        MutableLiveData<JSONArray> liveDataServingInfo = new MutableLiveData<>();
        String url = "https://api.nal.usda.gov/fdc/v1/food/"+fcdID+"?api_key=JOrlLA8RuHz2iQtAuveGa8jcxcqVipqpHvFzT5LX";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray foodServingArray = null;

                            int serving = 0;
                            if(jsonObject.getString("dataType").equals("Branded")){
                                serving = jsonObject.getInt("servingSize");

                                foodServingArray = new JSONArray();
                                foodServingArray.put(
                                        new JSONObject()
                                                .put("portionDescription", "1 Serving ("+serving+"g)")
                                                .put("gramWeight", serving)
                                        )
                                        .put( new JSONObject()
                                                .put("portionDescription", "1g")
                                                .put("gramWeight", 1));
                            }

                            if(jsonObject.getString("dataType").equals("Survey (FNDDS)")){
                                foodServingArray = jsonObject.getJSONArray("foodPortions");
//                                for(int i=0; i<foodServingArray.length(); i++){
//                                    JSONObject foodServing = foodServingArray.getJSONObject(i);
//                                    Log.d("Serving", foodServing.toString());
//                                }
                            }

                            liveDataServingInfo.postValue(foodServingArray);
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 10, 1f));
        queue.add(stringRequest);
        return liveDataServingInfo;
    }

    public MutableLiveData<Boolean> trackFood(FoodData foodData) {
        MutableLiveData<Boolean> mutableLiveDataInsertSuccess = new MutableLiveData<>();

        db.collection(UserConstants.KEY_COLLECTION_USERS)
                .document(FirebaseAuth.getInstance().getUid())
                .collection(NutritionTrackingConstants.KEY_COLLECTION_NUTRITION_TRACKING)
                .document(getCurrentDate())
                .collection(foodData.getMealType())
                .document(foodData.getFcdID()+"")
                .set(foodData.trackFoodMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mutableLiveDataInsertSuccess.postValue(true);
            }
        });

        return mutableLiveDataInsertSuccess;
    }
}