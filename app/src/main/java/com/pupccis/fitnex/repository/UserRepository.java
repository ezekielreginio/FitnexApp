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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.utilities.Constants.UserConstants;
import com.pupccis.fitnex.utilities.VideoConferencingConstants;
import com.pupccis.fitnex.validation.ValidationResult;

public class UserRepository {
    private static UserRepository instance;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(VideoConferencingConstants.Collections.KEY_PARENT);
    public static UserRepository getInstance(){
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    public static MutableLiveData<User> registerUser(User user){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        MutableLiveData<User> userLiveData = new MutableLiveData<>();

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseFirestore.getInstance().collection(GlobalConstants.KEY_COLLECTION_USERS)
                                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user.toMap());
                            User newUser = new User.Builder(user)
                                    .setUserID(FirebaseAuth.getInstance().getUid())
                                    .build();
                            userLiveData.postValue(newUser);
                        }
                        else{
                            userLiveData.postValue(null);
                            //Toast.makeText(FitnexRegister.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        return userLiveData;
    }

    public MutableLiveData<User> loginUser(String email, String password){
        MutableLiveData<User> userLoggedIn = new MutableLiveData<>();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() && task.getResult() != null){
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseFirestore.getInstance().collection(GlobalConstants.KEY_COLLECTION_USERS)
                            .document(FirebaseAuth.getInstance().getUid())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot documentSnapshot = task.getResult();

                            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener <String>() {
                                @Override
                                public void onComplete(@NonNull Task<String> task) {
                                    String token = task.getResult();
                                    sendFCMTokenToDatabase(task.getResult());
                                    User user = new User.Builder(
                                            documentSnapshot.get(UserConstants.KEY_USER_NAME).toString(),
                                            documentSnapshot.get(UserConstants.KEY_USER_EMAIL).toString()
                                    )
                                            .setUserID(documentSnapshot.getId())
                                            .setUserType(documentSnapshot.get(UserConstants.KEY_USER_TYPE).toString())
                                            .setAge(Integer.parseInt(documentSnapshot.get(UserConstants.KEY_USER_AGE).toString()))
                                            .setToken(token)
                                            .build();
                                    userLoggedIn.postValue(user);
                                }
                            });


                        }
                    });
                }
                else{
                    userLoggedIn.postValue(null);
                }
            }
        });
        return userLoggedIn;
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
    public Query searchTrainersQuery(String input){
        Query query = db.collection(UserConstants.KEY_COLLECTION_USERS).whereEqualTo("userType", "trainer").orderBy("name").startAt(input).endAt(input+"\uf8ff");

        return query;
    }

    private void sendFCMTokenToDatabase(String token){
        Log.d("Message Token fcm:", token);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference(VideoConferencingConstants.Collections.KEY_PARENT);
        Log.d("USer ID:", FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(VideoConferencingConstants.KEY_FCM_TOKEN).setValue(token);
        //DocumentReference documentReference = mDatabase.collection
        //mDatabase.update
    }

}
