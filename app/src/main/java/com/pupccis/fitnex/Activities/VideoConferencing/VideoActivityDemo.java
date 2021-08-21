package com.pupccis.fitnex.Activities.VideoConferencing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pupccis.fitnex.API.adapter.UsersAdapter;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Models.User;
import com.pupccis.fitnex.Activities.Login.FitnexLogin;
import com.pupccis.fitnex.Utilities.Constants;
import com.pupccis.fitnex.Utilities.PreferenceManager;
import com.pupccis.fitnex.Activities.VideoConferencing.listeners.UsersListener;

import java.util.ArrayList;
import java.util.List;

public class VideoActivityDemo extends AppCompatActivity implements View.OnClickListener, UsersListener {
    Button btnTest;
    TextView btnSignOut;
    private DatabaseReference mDatabase;
    private PreferenceManager preferenceManager;
    private List<User> users;
    private UsersAdapter usersAdapter;
    private TextView textErrorMessage;

    private String token;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_demo);

        preferenceManager = new PreferenceManager(getApplicationContext());

        btnSignOut =(TextView) findViewById(R.id.textSignOut);
        btnSignOut.setOnClickListener(this);



        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener <String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                token = task.getResult();

                sendFCMTokenToDatabase(task.getResult());
                getUsers();
            }
//            @Override
//            public void onComplete(@NonNull Task<InstallationTokenResult> task) {
//                sendFCMTokenToDatabase(task.getResult().getToken());
//            }
        });

        RecyclerView usersRecyclerView = findViewById(R.id.usersRecyclerView);
        textErrorMessage = findViewById(R.id.textErrorMessage);

        users = new ArrayList<>();
        usersAdapter = new UsersAdapter(users, this);
        usersRecyclerView.setAdapter(usersAdapter);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::getUsers);



    }

    private void getUsers(){
        swipeRefreshLayout.setRefreshing(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.KEY_COLLECTION_USERS);

        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                swipeRefreshLayout.setRefreshing(false);
                users.clear();
                for(DataSnapshot dataSnapshot : task.getResult().getChildren()){
                    String token = null;
                    try {
                        token = dataSnapshot.child(Constants.KEY_FCM_TOKEN).getValue().toString();
                    }
                    catch (Exception e){

                    }
                    if(preferenceManager.getString(Constants.KEY_USER_ID).equals(dataSnapshot.getKey())){
                        continue;
                    }

                    User user = new User();
                    user.setName(dataSnapshot.child(Constants.KEY_FULLNAME).getValue().toString());
                    user.setEmail(dataSnapshot.child(Constants.KEY_EMAIL).getValue().toString());
                    user.setToken(token);
                    users.add(user);
                }
                if(users.size() > 0){
                    usersAdapter.notifyDataSetChanged();
                }
                else{
                    textErrorMessage.setText(String.format("%s", "No users available"));
                    textErrorMessage.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case(R.id.textSignOut):
                signOut();
                break;
        }
    }

    private void sendFCMTokenToDatabase(String token){
        Log.d("Message Token fcm:", token);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.KEY_COLLECTION_USERS);
        Log.d("USer ID:", FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Constants.KEY_FCM_TOKEN).setValue(token);
        //DocumentReference documentReference = mDatabase.collection
        //mDatabase.update
    }

    private void signOut(){
        Toast.makeText(VideoActivityDemo.this, "Signing Out", Toast.LENGTH_SHORT).show();
        mDatabase.child(preferenceManager.getString(Constants.KEY_USER_ID)).child(Constants.KEY_FCM_TOKEN).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(VideoActivityDemo.this, "Signing Out...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), FitnexLogin.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VideoActivityDemo.this, "Failed to Sign out. Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void initiateVideoMeeting(User user) {
        if(user.getToken() == null || user.getToken().trim().isEmpty()){
            Toast.makeText(VideoActivityDemo.this, user.getName()+" is not available for meeting", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(getApplicationContext(), OutgoingInvitationActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("type", "video");
            startActivity(intent);
        }
    }

    @Override
    public void initiateAudioMeeting(User user) {
        if(user.getToken() == null || user.getToken().trim().isEmpty()){
            Toast.makeText(VideoActivityDemo.this, user.getName()+" is not available for call", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(VideoActivityDemo.this, "Starting a Audio Meeting with "+user.getName(), Toast.LENGTH_SHORT).show();
        }
    }

}