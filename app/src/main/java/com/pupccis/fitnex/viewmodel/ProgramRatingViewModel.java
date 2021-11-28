package com.pupccis.fitnex.viewmodel;

import android.net.Uri;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.model.ProgramRating;
import com.pupccis.fitnex.repository.ProgramRatingRepository;

public class ProgramRatingViewModel extends BaseObservable {
    private ProgramRatingRepository repository = new ProgramRatingRepository();

    private Uri rateProgramImage;
    private float rating;
    private String rateProgramImageFiletype;

    @Bindable
    private String ratingDescription;

    public Uri getRateProgramImage() {
        return rateProgramImage;
    }

    public float getRating() {
        return rating;
    }

    public String getRatingDescription() {
        return ratingDescription;
    }

    public String getRateProgramImageFiletype() {
        return rateProgramImageFiletype;
    }

    public void setRateProgramImage(Uri rateProgramImage) {
        this.rateProgramImage = rateProgramImage;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setRatingDescription(String ratingDescription) {
        this.ratingDescription = ratingDescription;
        notifyPropertyChanged(BR.ratingDescription);
    }

    public void setRateProgramImageFiletype(String rateProgramImageFiletype) {
        this.rateProgramImageFiletype = rateProgramImageFiletype;
    }

    public MutableLiveData<Boolean> saveRating(Program program, String userName){
        ProgramRating programRating = new ProgramRating(ratingDescription, userName, program.getProgramID(), rateProgramImage, rating, rateProgramImageFiletype);
        return repository.saveRating(programRating);
    }
}
