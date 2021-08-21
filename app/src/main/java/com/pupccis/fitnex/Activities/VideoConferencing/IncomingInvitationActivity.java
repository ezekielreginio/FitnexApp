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

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;
import com.pupccis.fitnex.Activities.VideoConferencing.network.ApiClient;
import com.pupccis.fitnex.Activities.VideoConferencing.network.ApiService;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomingInvitationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_invitation);

        ImageView imageMeetingType = findViewById(R.id.imageMeetingType);
        String meetingType = getIntent().getStringExtra(VideoConferencingConstants.REMOTE_MSG_MEETING_TYPE);

        if(meetingType != null){
            if(meetingType.equals("video")){
                imageMeetingType.setImageResource(R.drawable.ic_video);
            }
        }

        TextView textFirstChar = findViewById(R.id.textFirstChar);
        TextView textUsername = findViewById(R.id.textUsername);
        TextView textEmail = findViewById(R.id.textEmail);

        textUsername.setText(getIntent().getStringExtra(VideoConferencingConstants.KEY_FULLNAME));
        textEmail.setText(getIntent().getStringExtra(VideoConferencingConstants.KEY_EMAIL));

        ImageView imageAcceptInvitation = findViewById(R.id.imageAcceptInvitation);
        imageAcceptInvitation.setOnClickListener(view -> sendInvitationResponse(
                VideoConferencingConstants.REMOTE_MSG_INVITATION_ACCEPTED,
                getIntent().getStringExtra(VideoConferencingConstants.REMOTE_MSG_INVITER_TOKEN)
        ));

        ImageView imageRejectInvitation = findViewById(R.id.imageRejectInvitation);
        imageRejectInvitation.setOnClickListener(view -> sendInvitationResponse(
                VideoConferencingConstants.REMOTE_MSG_INVITATION_REJECTED,
                getIntent().getStringExtra(VideoConferencingConstants.REMOTE_MSG_INVITER_TOKEN)
        ));
    }

    private void sendInvitationResponse(String type, String receiverToken){
        try{
            JSONArray tokens = new JSONArray();
            tokens.put(receiverToken);

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put(VideoConferencingConstants.REMOTE_MSG_TYPE, VideoConferencingConstants.REMOTE_MSG_INVITATION_RESPONSE);
            data.put(VideoConferencingConstants.REMOTE_MSG_INVITATION_RESPONSE, type);

            body.put(VideoConferencingConstants.REMOTE_MSG_DATA, data);
            body.put(VideoConferencingConstants.REMOTE_MSG_REGISTRATION_IDS, tokens);

            sendRemoteMessage(body.toString(), type);
        }
        catch (Exception exception){
            Toast.makeText(IncomingInvitationActivity.this, exception.getMessage(),Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void sendRemoteMessage(String remoteMessageBody, String type){
        Log.d("Message Body", remoteMessageBody);
        ApiClient.getClient().create(ApiService.class).sendRemoteMessage(
                VideoConferencingConstants.getRemoteMessageHeaders(), remoteMessageBody
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    if(type.equals(VideoConferencingConstants.REMOTE_MSG_INVITATION_ACCEPTED)){
                        //Toast.makeText(IncomingInvitationActivity.this, "Invitation Accepted",Toast.LENGTH_SHORT).show();
                        try {
                            URL serverURL = new URL("https://meet.jit.si");
                            JitsiMeetConferenceOptions conferenceOptions =
                                    new JitsiMeetConferenceOptions.Builder()
                                    .setServerURL(serverURL)
                                    .setWelcomePageEnabled(false)
                                    .setRoom(getIntent().getStringExtra(VideoConferencingConstants.REMOTE_MSG_MEETING_ROOM))
                                    .build();
                            JitsiMeetActivity.launch(IncomingInvitationActivity.this, conferenceOptions);
                            finish();
                        }
                        catch (Exception exception){
                            Toast.makeText(IncomingInvitationActivity.this, exception.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(IncomingInvitationActivity.this, "Invitation Rejected",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else{
                    Toast.makeText(IncomingInvitationActivity.this, response.message(),Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(IncomingInvitationActivity.this, t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private BroadcastReceiver invitationResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra(VideoConferencingConstants.REMOTE_MSG_INVITATION_RESPONSE);
            if(type != null){
                if(type.equals(VideoConferencingConstants.REMOTE_MSG_INVITATION_CANCELLED)){
                    Toast.makeText(context, "Invitation Cancelled",Toast.LENGTH_SHORT).show();
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