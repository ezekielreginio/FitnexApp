package com.pupccis.fitnex.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.utilities.Constants.UserConstants;
import com.pupccis.fitnex.validation.ValidationResult;

public class UserRepository {
    private static UserRepository instance;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static UserRepository getInstance(){
        if(instance == null){
            instance = new UserRepository();
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

    public MutableLiveData<ValidationResult> duplicateEmailLiveResponse(String email){
        MutableLiveData<ValidationResult> emailValidationLiveData = new MutableLiveData<>();
        emailValidationLiveData.setValue(null);

        Query query = db.collection(GlobalConstants.KEY_COLLECTION_USERS).whereEqualTo(UserConstants.KEY_USER_EMAIL, email);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.getResult().size() == 0)
                    emailValidationLiveData.postValue(new ValidationResult(true, null));
                else
                    emailValidationLiveData.postValue(new ValidationResult(false, "This email is already registered!"));
            }
        });
        return emailValidationLiveData;
    }

}
