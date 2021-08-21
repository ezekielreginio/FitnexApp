package com.pupccis.fitnex.API.firebase;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;
import com.pupccis.fitnex.Activities.VideoConferencing.IncomingInvitationActivity;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("FCM", "Token: "+token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String type = remoteMessage.getData().get(VideoConferencingConstants.REMOTE_MSG_TYPE);

        Log.d("Nag accept", remoteMessage.getData().toString());
        Log.d("Type", type);

        if(type != null){
            if(type.equals(VideoConferencingConstants.REMOTE_MSG_INVITATION)) {
                Intent intent = new Intent(getApplicationContext(), IncomingInvitationActivity.class);

                intent.putExtra(
                        VideoConferencingConstants.REMOTE_MSG_MEETING_TYPE,
                        remoteMessage.getData().get(VideoConferencingConstants.REMOTE_MSG_MEETING_TYPE)
                );

                intent.putExtra(
                        "fullname",
                        remoteMessage.getData().get("first_name")
                );

                intent.putExtra(
                        "last_name",
                        remoteMessage.getData().get("last_name")
                );

                intent.putExtra(
                        VideoConferencingConstants.KEY_EMAIL,
                        remoteMessage.getData().get(VideoConferencingConstants.KEY_EMAIL)
                );

                intent.putExtra(
                        VideoConferencingConstants.REMOTE_MSG_INVITER_TOKEN,
                        remoteMessage.getData().get(VideoConferencingConstants.REMOTE_MSG_INVITER_TOKEN)
                );
                intent.putExtra(
                        VideoConferencingConstants.REMOTE_MSG_MEETING_ROOM,
                        remoteMessage.getData().get(VideoConferencingConstants.REMOTE_MSG_MEETING_ROOM)
                );
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
            else if(type.equals(VideoConferencingConstants.REMOTE_MSG_INVITATION_RESPONSE)){

                Intent intent = new Intent(VideoConferencingConstants.REMOTE_MSG_INVITATION_RESPONSE);

                intent.putExtra(
                        VideoConferencingConstants.REMOTE_MSG_INVITATION_RESPONSE,
                        remoteMessage.getData().get(VideoConferencingConstants.REMOTE_MSG_INVITATION_RESPONSE)
                );

                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            }
        }

    }
}
