package com.pupccis.fitnex.model;

import android.net.Uri;

import com.pupccis.fitnex.utilities.Constants.RoutineConstants;
import com.pupccis.fitnex.utilities.Constants.UserConstants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TrainerApplicant implements Serializable {
    private final String email, name, birthdate, filetypeResume, filetypeProfilePicture, phoneNumber;
    private final Uri profilePicture, resume;

    public TrainerApplicant(Builder builder) {
        this.email = builder.email;
        this.name = builder.name;
        this.birthdate = builder.birthdate;
        this.phoneNumber = builder.phoneNumber;
        this.profilePicture = builder.profilePicture;
        this.resume = builder.resume;
        this.filetypeResume  = builder.filetypeResume;
        this.filetypeProfilePicture = builder.filetypeProfilePicture;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public Uri getProfilePicture() {
        return profilePicture;
    }

    public Uri getResume() {
        return resume;
    }

    public String getFiletypeResume() {
        return filetypeResume;
    }

    public String getFiletypeProfilePicture() {
        return filetypeProfilePicture;
    }

    public Map<String, Object> toMap(String profilePictureUrl, String resumeUrl) {
        HashMap<String, Object> result = new HashMap<>();
        result.put(UserConstants.KEY_USER_NAME, name);
        result.put(UserConstants.KEY_USER_EMAIL, email);
        result.put(UserConstants.KEY_USER_BIRTHDATE, birthdate);
        result.put(UserConstants.KEY_USER_APPLICATION_STATUS, 0);
        result.put(UserConstants.KEY_USER_PROFILE_PICTURE, profilePictureUrl);
        result.put(UserConstants.KEY_USER_RESUME, resumeUrl);
        result.put(UserConstants.KEY_PHONE_NUMBER, phoneNumber);
        return result;
    }

    public static class Builder{
        private final String email, name, birthdate, filetypeResume, filetypeProfilePicture, phoneNumber;
        private final Uri profilePicture, resume;

        public Builder(String email, String name, String birthdate, String phoneNumber, Uri profilePicture, Uri resume, String filetypeProfilePicture, String filetypeResume) {
            this.email = email;
            this.name = name;
            this.birthdate = birthdate;
            this.phoneNumber = phoneNumber;
            this.profilePicture = profilePicture;
            this.resume = resume;
            this.filetypeResume = filetypeResume;
            this.filetypeProfilePicture = filetypeProfilePicture;
        }

        public TrainerApplicant build(){
            TrainerApplicant trainerApplicant = new TrainerApplicant(this);
            return trainerApplicant;
        }
    }
}
