package com.pupccis.fitnex.utilities;

import java.util.HashMap;

public class VideoConferencingConstants {
    public static class Collections{
        public static final String KEY_PARENT = "users";
    }

    public static final String KEY_FULLNAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_AGE = "age";
    public static final String KEY_FCM_TOKEN = "fcm_token";

    public static final String KEY_PREFERENCE_NAME = "videoMeetingPreference";
    public static final String KEY_IS_SIGNED_ID = "isSignedIn";

    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";

    public static final String REMOTE_MSG_TYPE = "type";
    public static final String REMOTE_MSG_INVITATION = "invitation";
    public static final String REMOTE_MSG_MEETING_TYPE = "meetingType";
    public static final String REMOTE_MSG_INVITER_TOKEN = "inviterToken";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";

    public static final String REMOTE_MSG_INVITATION_RESPONSE = "invitationResponse";

    public static final String REMOTE_MSG_INVITATION_ACCEPTED = "accepted";
    public static final String REMOTE_MSG_INVITATION_REJECTED = "rejected";
    public static final String REMOTE_MSG_INVITATION_CANCELLED = "cancelled";

    public static final String REMOTE_MSG_MEETING_ROOM = "meetingRoom";

    //Program FitnessClassAdapter Constants


    public static HashMap<String, String> getRemoteMessageHeaders()
    {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(
                VideoConferencingConstants.REMOTE_MSG_AUTHORIZATION,
                "key=AAAA-2JlCUc:APA91bHt3idPUgJk7Oz5Ogme0k9PhulXcbP_0pi4c9Iu2_W3YHUhCeofepZJ46wZ0UeQyU91iaxLCkAIpKcGdcKGefMjpbsrd522hXKA6cBcCDi1pFZ4nzLZLu64y0fxqr7r_N-1v5hS"
        );
        headers.put(VideoConferencingConstants.REMOTE_MSG_CONTENT_TYPE, "application/json");
        return headers;
    }
}
