package com.pupccis.fitnex.Activities.VideoConferencing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;
import com.pupccis.fitnex.Utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.Activities.VideoConferencing.network.ApiClient;
import com.pupccis.fitnex.Activities.VideoConferencing.network.ApiService;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutgoingInvitationActivity extends AppCompatActivity {

    private UserPreferences userPreferences;
    private String inviterToken = null;

    String meetingRoom = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing_invitation);

        userPreferences = new UserPreferences(getApplicationContext());

        ImageView imageMeetingType = findViewById(R.id.imageMeetingType);
        String meetingType = getIntent().getStringExtra("type");

        if(meetingType != null){
            if(meetingType.equals("video")){
                imageMeetingType.setImageResource(R.drawable.ic_video);
            }
        }

        TextView textFirstChar = findViewById(R.id.textFirstChar);
        TextView textUsername = findViewById(R.id.textUsername);
        TextView textEmail = findViewById(R.id.textEmail);

        User user = (User) getIntent().getSerializableExtra("user");
        if(user!=null){
            textFirstChar.setText(user.getName().substring(0, 1));
            textUsername.setText(user.getName());
            textEmail.setText(user.getEmail());
        }

        ImageView imageStopInvitation = findViewById(R.id.imageStopInvitation);
        imageStopInvitation.setOnClickListener(view -> {
            if(user != null){
                cancelInvitation(user.getToken());
                finish();
            }
        });

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener <String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                inviterToken = task.getResult();
                if(meetingType != null && user != null){
                    initiateMeeting(meetingType, user.getToken());
                }
            }
        });


    }

    private void initiateMeeting(String meetingType, String receiverToken){
        try {
            JSONArray tokens = new JSONArray();
            tokens.put(receiverToken);

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put(VideoConferencingConstants.REMOTE_MSG_TYPE, VideoConferencingConstants.REMOTE_MSG_INVITATION);
            data.put(VideoConferencingConstants.REMOTE_MSG_MEETING_TYPE, meetingType);
            data.put("first_name", userPreferences.getString(VideoConferencingConstants.KEY_FULLNAME));
            data.put("last_name", null);
            data.put(VideoConferencingConstants.KEY_EMAIL, userPreferences.getString(VideoConferencingConstants.KEY_EMAIL));
            data.put(VideoConferencingConstants.REMOTE_MSG_INVITER_TOKEN, inviterToken);

            meetingRoom =
                    userPreferences.getString(VideoConferencingConstants.KEY_USER_ID) + "_"+
                            UUID.randomUUID().toString().substring(0,5);
            data.put(VideoConferencingConstants.REMOTE_MSG_MEETING_ROOM, meetingRoom);

            body.put(VideoConferencingConstants.REMOTE_MSG_DATA, data);
            body.put(VideoConferencingConstants.REMOTE_MSG_REGISTRATION_IDS, tokens);

            sendRemoteMessage(body.toString(), VideoConferencingConstants.REMOTE_MSG_INVITATION);
        }
        catch (Exception exception){
            Toast.makeText(OutgoingInvitationActivity.this, exception.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void sendRemoteMessage(String remoteMessageBody, String type){
        ApiClient.getClient().create(ApiService.class).sendRemoteMessage(
                VideoConferencingConstants.getRemoteMessageHeaders(), remoteMessageBody
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    if(type.equals(VideoConferencingConstants.REMOTE_MSG_INVITATION)){
                        Toast.makeText(OutgoingInvitationActivity.this, "Invitation Sent successfully",Toast.LENGTH_SHORT).show();
                    }
                    else if (type.equals(VideoConferencingConstants.REMOTE_MSG_INVITATION_RESPONSE)){
                        Toast.makeText(OutgoingInvitationActivity.this, "Invitation Cancelled",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else{
                    Toast.makeText(OutgoingInvitationActivity.this, response.message(),Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(OutgoingInvitationActivity.this, t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cancelInvitation(String receiverToken){
        try{
            JSONArray tokens = new JSONArray();
            tokens.put(receiverToken);

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put(VideoConferencingConstants.REMOTE_MSG_TYPE, VideoConferencingConstants.REMOTE_MSG_INVITATION_RESPONSE);
            data.put(VideoConferencingConstants.REMOTE_MSG_INVITATION_RESPONSE, VideoConferencingConstants.REMOTE_MSG_INVITATION_CANCELLED);

            body.put(VideoConferencingConstants.REMOTE_MSG_DATA, data);
            body.put(VideoConferencingConstants.REMOTE_MSG_REGISTRATION_IDS, tokens);

            sendRemoteMessage(body.toString(), VideoConferencingConstants.REMOTE_MSG_INVITATION_CANCELLED);
        }
        catch (Exception exception){
            Toast.makeText(this, exception.getMessage(),Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private BroadcastReceiver invitationResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Broadcast Received", "Nareceive ang broadcast");
            String type = intent.getStringExtra(VideoConferencingConstants.REMOTE_MSG_INVITATION_RESPONSE);
            if(type != null){
                if(type.equals(VideoConferencingConstants.REMOTE_MSG_INVITATION_ACCEPTED)){
                    try {
                        URL serverURL = new URL("https://meet.jit.si");
                        JitsiMeetConferenceOptions conferenceOptions =
                                new JitsiMeetConferenceOptions.Builder()
                                .setServerURL(serverURL)
                                .setWelcomePageEnabled(false)
                                .setRoom(meetingRoom)
                                .build();
                        JitsiMeetActivity.launch(OutgoingInvitationActivity.this, conferenceOptions);
                        finish();
                    }
                    catch (Exception exception){
                        Toast.makeText(context, exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
                else if(type.equals(VideoConferencingConstants.REMOTE_MSG_INVITATION_REJECTED)){
                    Toast.makeText(context, "Invitation Rejected",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                invitationResponseReceiver,
                new IntentFilter(VideoConferencingConstants.REMOTE_MSG_INVITATION_RESPONSE)
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(
                invitationResponseReceiver
        );
    }


}