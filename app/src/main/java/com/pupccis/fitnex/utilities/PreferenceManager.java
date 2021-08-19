package com.pupccis.fitnex.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public PreferenceManager(Context context){
        sharedPreferences = context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE);
         editor= sharedPreferences.edit();
    }

    public void putBoolean(String key, Boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public Boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key, false);
    }

    public void putString(String key, String value){

        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key){
        return sharedPreferences.getString(key, null);
    }

    public void clearPreferences(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
