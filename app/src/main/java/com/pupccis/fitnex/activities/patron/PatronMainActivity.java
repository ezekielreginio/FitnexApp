package com.pupccis.fitnex.activities.patron;

import static androidx.core.content.ContextCompat.getSystemService;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.viewmodel.PatronViewModel;

public class PatronMainActivity extends AppCompatActivity {
    private PatronViewModel patronViewModel = new PatronViewModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patron_main);

    }

    public PatronViewModel getPatronViewModel() {
        return patronViewModel;
    }

}