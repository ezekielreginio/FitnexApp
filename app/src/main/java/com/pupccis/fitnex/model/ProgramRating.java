package com.pupccis.fitnex.model;

import static com.pupccis.fitnex.utilities.Constants.ProgramRatingConstants.KEY_RATING;
import static com.pupccis.fitnex.utilities.Constants.ProgramRatingConstants.KEY_RATING_DESCRIPTION;
import static com.pupccis.fitnex.utilities.Constants.ProgramRatingConstants.KEY_RATING_PHOTO_URL;
import static com.pupccis.fitnex.utilities.Constants.ProgramRatingConstants.KEY_RATING_USERNAME;

import android.net.Uri;
import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProgramRating implements Serializable {
    private String ratingDescription, userName, programID, rateProgramImageFileType, photoURL;
    private Uri rateProgramImage;
    private float rating;

    public ProgramRating() {
        Log.e("Constructor", "called");
    }

    public ProgramRating(String ratingDescription, String userName, String programID, Uri rateProgramImage, float rating, String rateProgramImageFileType) {
        this.ratingDescription = ratingDescription;
        this.userName = userName;
        this.programID = programID;
        this.rateProgramImage = rateProgramImage;
        this.rating = rating;
        this.rateProgramImageFileType = rateProgramImageFileType;
    }

    public String getRatingDescription() {
        return ratingDescription;
    }

    public void setRatingDescription(String ratingDescription) {
        this.ratingDescription = ratingDescription;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProgramID() {
        return programID;
    }

    public void setProgramID(String programID) {
        this.programID = programID;
    }

    public Uri getRateProgramImage() {
        return rateProgramImage;
    }

    public void setRateProgramImage(Uri rateProgramImage) {
        this.rateProgramImage = rateProgramImage;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getRateProgramImageFileType() {
        return rateProgramImageFileType;
    }

    public void setRateProgramImageFileType(String rateProgramImageFileType) {
        this.rateProgramImageFileType = rateProgramImageFileType;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }



    public Map<String, Object> map() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(KEY_RATING_DESCRIPTION, ratingDescription);
        result.put(KEY_RATING_USERNAME, userName);
        result.put(KEY_RATING_PHOTO_URL, photoURL);
        result.put(KEY_RATING, rating);
        return result;
    }
}
