package com.pupccis.fitnex.activities.VideoConferencing.listeners;

import com.pupccis.fitnex.model.User;

public interface UsersListener {

    void initiateVideoMeeting(User user);

    void initiateAudioMeeting(User user);

}
