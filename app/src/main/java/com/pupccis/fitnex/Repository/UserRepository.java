package com.pupccis.fitnex.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class UserRepository {
    private static ProgramsRepository instance;

    public static ProgramsRepository getInstance(){
        if(instance == null){
            instance = new ProgramsRepository();
        }
        return instance;
    }

    public static MutableLiveData<Boolean> duplicateEmailLiveResponse(String email){
        MutableLiveData<Boolean> booleanMutableLiveData = new MutableLiveData<>();
        booleanMutableLiveData.setValue(null);
        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if(task.getResult().getSignInMethods().isEmpty())
                    booleanMutableLiveData.postValue(true);
                else
                    booleanMutableLiveData.postValue(false);
            }
        });
        return booleanMutableLiveData;
    }

}
