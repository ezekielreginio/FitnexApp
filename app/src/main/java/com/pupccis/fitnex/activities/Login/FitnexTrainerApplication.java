package com.pupccis.fitnex.activities.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pupccis.fitnex.api.JavaMailAPI;
import com.pupccis.fitnex.api.MailBody;
import com.pupccis.fitnex.R;

public class FitnexTrainerApplication extends AppCompatActivity {
    private EditText recipient;
    private TextView applyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitnex_trainer_application);

        recipient = (EditText) findViewById(R.id.editTextApplicationEmail);
        applyButton = (Button) findViewById(R.id.buttonApplyButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { sendClick(); }
        });
    }

    private void sendClick(){
        String recipientEmail = recipient.getText().toString().trim();
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, recipientEmail, MailBody.getSubject(), MailBody.getBody());
        javaMailAPI.execute();
    }
    public void onLoginClick (View view){
        startActivity(new Intent(this, FitnexLogin.class ));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}