package com.pupccis.fitnex.handlers.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.validation.ValidationResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewHandler {
    static String [] category = new String[]{
      "Select an Item",
      "Cardio",
      "Sports",
      "Strength"
    };

    public static void errorHandler(TextInputLayout textInputLayout, ValidationResult result) {
        textInputLayout.setErrorEnabled(!result.isValid());
        textInputLayout.setError(result.getErrorMsg());
        if(result.getHelperMsg() != null)
            textInputLayout.getEditText().setError(result.getHelperMsg());
    }

    public static boolean uiErrorHandler(ArrayList<TextInputLayout> textInputLayouts){
        boolean isInvalid = false;
        for(TextInputLayout field: textInputLayouts){
            if(field.isErrorEnabled()) {
                Log.d("Field", "Invalid");
                isInvalid = true;
            }
            else if(field.getEditText().getText()==null || field.getEditText().getText().toString().isEmpty()){
                Log.d("Field", "Empty");
                field.setErrorEnabled(true);
                field.setError("This Field is Required");
                isInvalid = true;
            }
        }

        return isInvalid;
    }


    public static void rotateAnimation(Animation rotateAnimation, ImageView button) {
        button.setImageResource(R.drawable.ic_close_button);
        button.startAnimation(rotateAnimation);
    }

    public static void setDropdown(Spinner spinner, Context context, String [] stringArray){
        List<String> spinnerList = new ArrayList<>(Arrays.asList(stringArray));
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.category, R.layout.spinner_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, spinnerList){
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                    return false;
                else
                    return true;
            }
        };
        spinner.setAdapter(adapter);

    }
}
