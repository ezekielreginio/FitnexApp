package com.pupccis.fitnex.utilities.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.utilities.VideoConferencingConstants;

public class UserPreferences {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public UserPreferences(Context context){
        sharedPreferences = context.getSharedPreferences(VideoConferencingConstants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE);
         editor= sharedPreferences.edit();
    }

    public void setUserPreferences(User user){
        this.putBoolean(VideoConferencingConstants.KEY_IS_SIGNED_ID, true);
        this.putString(VideoConferencingConstants.KEY_FULLNAME, user.getName());
        this.putString(VideoConferencingConstants.KEY_EMAIL, user.getEmail());
        this.putString(VideoConferencingConstants.KEY_AGE, user.getAge()+"");
        this.putString(VideoConferencingConstants.KEY_USER_ID, user.getUserID());
        this.putString("usertype", user.getUserType());
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
