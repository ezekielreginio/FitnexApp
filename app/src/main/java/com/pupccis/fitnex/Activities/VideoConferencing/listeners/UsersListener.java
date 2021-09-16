package com.pupccis.fitnex.Activities.VideoConferencing.listeners;

import com.pupccis.fitnex.Model.User;

public interface UsersListener {

    void initiateVideoMeeting(User user);

    void initiateAudioMeeting(User user);

}
