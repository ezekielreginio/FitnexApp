package com.pupccis.fitnex.Models.DAO;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pupccis.fitnex.Activities.Main.Trainer.AddProgram;
import com.pupccis.fitnex.Activities.Main.Trainer.TrainerDashboard;
import com.pupccis.fitnex.Models.Program;
import com.pupccis.fitnex.Models.User;
import com.pupccis.fitnex.Utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramDAO {
    private String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS);
    public void createProgram(Program program){
        String program_key = FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                .child(user_id)
                .push().getKey();

        FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                .child(program_key)
                .setValue(program);

    }

    public List<Program> readPrograms(String userID){
        List<Program> programsList = new ArrayList<>();
        mDatabase.child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()){
                    Program program = new Program();
                    program.setName(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_NAME).getValue().toString());
                    program.setDescription(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_DESCRIPTION).getValue().toString());
                    program.setCategory(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_CATEGORY).getValue().toString());
                    program.setSessionNumber(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_SESSION_NUMBER).getValue().toString());
                    program.setDuration(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_DURATION).getValue().toString());
                    programsList.add(program);
                    Log.d("Name: ",program.getName());
                    Log.d("List: ",programsList.toString());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error", e.getMessage());
            }
        });
        return programsList;
    }

    public void updateProgram(Program program){
        Map postValues = program.toMap();
        mDatabase.child(program.getProgramID()).updateChildren(postValues);
    }

    public void deleteProgram(Program program){
        mDatabase.child(program.getProgramID()).removeValue();
    }

    public void joinProgram(Program program){
        mDatabase.child(program.getProgramID())
                .child(ProgramConstants.KEY_PROGRAM_SUBSCRIBERS)
                .child(user_id)
                .setValue("subscribed");


        FirebaseDatabase.getInstance().getReference(VideoConferencingConstants.KEY_COLLECTION_USERS)
                .child(user_id)
                .child(ProgramConstants.KEY_PROGRAM_USER_SUBSCRIBED)
                .child(program.getProgramID())
                .setValue("subscribed");
    }

}

