package com.pupccis.fitnex.API.globals;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.pupccis.fitnex.R;

import java.util.HashMap;

public class Global{
    public HashMap<String, String> getTextViews(LinearLayout parent, Resources resources){
        HashMap<String, String> hashMap = new HashMap<>();

        for(int i=0; i<parent.getChildCount(); i++){
            TextInputLayout child = (TextInputLayout) parent.getChildAt(i);
            FrameLayout frameLayout = (FrameLayout) child.getChildAt(0);
            TextView textfield = (TextView) frameLayout.getChildAt(0);
            //getResources();
            Log.d("Test Field: ", ""+resources.getResourceEntryName(textfield.getId()));
            //hashMap.put(textfield.getTag().toString(), textfield.getText().toString());
        }
        return hashMap;
    }
}
