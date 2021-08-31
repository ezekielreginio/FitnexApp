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
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramDAO {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS);
    public void createProgram(Program program){
        String program_key = FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .push().getKey();

        FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(program_key)
                .setValue(program);

        FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS_LIST)
                .child(program_key)
                .setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
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
//        mDatabase = ;
//        Log.d("New Name: ", program.getName());
//        String key = mDatabase.push().getKey();
        Map postValues = program.toMap();

        mDatabase.child(program.getProgramTrainerID()).child(program.getProgramID()).updateChildren(postValues);
    }

    public void deleteProgram(Program program){
        mDatabase.child(program.getProgramTrainerID()).child(program.getProgramID()).removeValue();
        FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS_LIST)
                .child(program.getProgramID()).removeValue();
    }

}

