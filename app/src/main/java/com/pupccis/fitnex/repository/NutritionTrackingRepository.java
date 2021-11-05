package com.pupccis.fitnex.repository;

import static com.pupccis.fitnex.api.DateFormatter.getCurrentDate;
import static com.pupccis.fitnex.validation.NullValidation.nonNullDouble;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;
import com.pupccis.fitnex.activities.nutritiontracking.enums.MacroNutrients;
import com.pupccis.fitnex.activities.nutritiontracking.enums.Meals;
import com.pupccis.fitnex.model.FoodData;
import com.pupccis.fitnex.utilities.Constants.UserConstants;
import com.pupccis.fitnex.utilities.Constants.nutritionTrackingConstants.NutritionTrackingConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
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

        DocumentReference currentFoodIntake = db.collection(UserConstants.KEY_COLLECTION_USERS)
                .document(FirebaseAuth.getInstance().getUid())
                .collection(NutritionTrackingConstants.KEY_COLLECTION_NUTRITION_TRACKING)
                .document(getCurrentDate());

        currentFoodIntake.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Double totalCalories = (Double) task.getResult().get(NutritionTrackingConstants.KEY_NUTRITION_TOTAL_CALORIES);
                Double totalCarbs =(Double) task.getResult().get(NutritionTrackingConstants.KEY_NUTRITION_TOTAL_CARBS);
                Double totalProtein =(Double) task.getResult().get(NutritionTrackingConstants.KEY_NUTRITION_TOTAL_PROTEIN);
                Double totalFats =(Double) task.getResult().get(NutritionTrackingConstants.KEY_NUTRITION_TOTAL_FATS);

                Double mealTotalCalories = (Double) task.getResult().get(NutritionTrackingConstants.mealData(Meals.valueOf(foodData.getMealType()), MacroNutrients.CALORIES));
                Double mealTotalCarbs = (Double) task.getResult().get(NutritionTrackingConstants.mealData(Meals.valueOf(foodData.getMealType()), MacroNutrients.CARBS));
                Double mealTotalProtein = (Double) task.getResult().get(NutritionTrackingConstants.mealData(Meals.valueOf(foodData.getMealType()), MacroNutrients.PROTEIN));
                Double mealTotalFats = (Double) task.getResult().get(NutritionTrackingConstants.mealData(Meals.valueOf(foodData.getMealType()), MacroNutrients.FATS));

                FoodData totalFoodData = new FoodData.Builder()
                        .mealType(foodData.getMealType())
                        .totalCalories(nonNullDouble(totalCalories) + foodData.getCalories())
                        .totalCarbs(nonNullDouble(totalCarbs) + foodData.getCarbs())
                        .totalFat(nonNullDouble(totalFats) + foodData.getFats())
                        .totalProtein(nonNullDouble(totalProtein) + foodData.getProtein())
                        .build();

                FoodData totalMealFoodData = new FoodData.Builder()
                        .totalCalories(nonNullDouble(mealTotalCalories) + foodData.getCalories())
                        .totalCarbs(nonNullDouble(mealTotalCarbs) + foodData.getCarbs())
                        .totalFat(nonNullDouble(mealTotalFats) + foodData.getFats())
                        .totalProtein(nonNullDouble(mealTotalProtein) + foodData.getProtein())
                        .build();

                currentFoodIntake.set(totalFoodData.totalDataMap(totalMealFoodData), SetOptions.merge());
            }
        });



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

    public MutableLiveData<HashMap<String, Object>> getMealData(Meals meal) {
        MutableLiveData<HashMap<String, Object>> liveMealData = new MutableLiveData<>();
        HashMap<String, Object> mealData = new HashMap<>();
        DecimalFormat df = new DecimalFormat("####0.##");

        db.collection(UserConstants.KEY_COLLECTION_USERS)
                .document(FirebaseAuth.getInstance().getUid())
                .collection(NutritionTrackingConstants.KEY_COLLECTION_NUTRITION_TRACKING)
                .document(getCurrentDate())
                .collection(meal.toString()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<String> foodList = new ArrayList<>();
                int calories = 0;
                double carbs = 0.0;
                double protein = 0.0;
                double fats = 0.0;
                for(DocumentSnapshot snapshot : value.getDocuments()){
                    foodList.add(snapshot.getString(NutritionTrackingConstants.KEY_NUTRITION_FOOD_NAME));
                    calories = calories + (int)(double)snapshot.get(NutritionTrackingConstants.KEY_NUTRITION_CALORIES);
                    carbs = carbs + (double)snapshot.get(NutritionTrackingConstants.KEY_NUTRITION_CARBS);
                    protein = protein + (double)snapshot.get(NutritionTrackingConstants.KEY_NUTRITION_PROTEIN);
                    fats = fats + (double)snapshot.get(NutritionTrackingConstants.KEY_NUTRITION_FATS);
                }

                mealData.put("foodList", foodList);
                Log.d("Calories", calories+"");
                mealData.put(NutritionTrackingConstants.KEY_NUTRITION_CALORIES, calories);
                mealData.put(NutritionTrackingConstants.KEY_NUTRITION_PROTEIN, protein);
                mealData.put(NutritionTrackingConstants.KEY_NUTRITION_CARBS, carbs);
                mealData.put(NutritionTrackingConstants.KEY_NUTRITION_FATS, fats);
                liveMealData.postValue(mealData);
            }
        });

        return liveMealData;
    }
}
