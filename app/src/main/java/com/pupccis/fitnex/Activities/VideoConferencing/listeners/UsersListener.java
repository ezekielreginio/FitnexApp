package com.pupccis.fitnex.Activities.VideoConferencing.listeners;

import com.pupccis.fitnex.model.User;

public interface UsersListener {

    void initiateVideoMeeting(User user);

    void initiateAudioMeeting(User user);

}
