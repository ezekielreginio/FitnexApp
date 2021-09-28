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
    static List<String> categoryList = new ArrayList<>(Arrays.asList(category));

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

    public static void setDropdown(Spinner spinner, Context context){
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.category, R.layout.spinner_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, categoryList){
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                    return false;
                else
                    return true;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
