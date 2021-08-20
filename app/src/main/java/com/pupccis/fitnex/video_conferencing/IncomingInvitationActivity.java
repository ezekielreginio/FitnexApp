package com.pupccis.fitnex.video_conferencing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.utilities.Constants;

public class IncomingInvitationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_invitation);

        ImageView imageMeetingType = findViewById(R.id.imageMeetingType);
        String meetingType = getIntent().getStringExtra(Constants.REMOTE_MSG_MEETING_TYPE);

        if(meetingType != null){
            if(meetingType.equals("video")){
                imageMeetingType.setImageResource(R.drawable.ic_video);
            }
        }

        TextView textFirstChar = findViewById(R.id.textFirstChar);
        TextView textUsername = findViewById(R.id.textUsername);
        TextView textEmail = findViewById(R.id.textEmail);

        textUsername.setText(getIntent().getStringExtra(Constants.KEY_FULLNAME));
        textEmail.setText(getIntent().getStringExtra(Constants.KEY_EMAIL));
    }
}