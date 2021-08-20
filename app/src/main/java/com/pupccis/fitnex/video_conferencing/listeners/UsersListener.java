package com.pupccis.fitnex.video_conferencing.listeners;

import com.pupccis.fitnex.User;

public interface UsersListener {

    void initiateVideoMeeting(User user);

    void initiateAudioMeeting(User user);

}
