package com.pupccis.fitnex.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.utilities.Constants.GlobalConstants;

public class UserRepository {
    private static ProgramsRepository instance;

    public static ProgramsRepository getInstance(){
        if(instance == null){
            instance = new ProgramsRepository();
        }
        return instance;
    }

    public static MutableLiveData<User> registerUser(User user){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();;
        MutableLiveData<User> userLiveData = new MutableLiveData<>();

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){
                            FirebaseFirestore.getInstance().collection(GlobalConstants.KEY_COLLECTION_USERS)
                                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user.toMap());

                            userLiveData.postValue(user);
                        }
                        else{
                            userLiveData.postValue(null);
                            //Toast.makeText(FitnexRegister.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        return userLiveData;
    }

    public static MutableLiveData<Boolean> duplicateEmailLiveResponse(String email){
        MutableLiveData<Boolean> booleanMutableLiveData = new MutableLiveData<>();
        booleanMutableLiveData.setValue(null);
        if(email != null && !email.isEmpty()){
            FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                    if(task.getResult().getSignInMethods().isEmpty())
                        booleanMutableLiveData.postValue(true);
                    else
                        booleanMutableLiveData.postValue(false);
                }
            });
        }

        return booleanMutableLiveData;
    }

}
