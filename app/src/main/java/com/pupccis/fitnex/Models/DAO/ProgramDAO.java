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
import java.util.List;

public class ProgramDAO {
    private DatabaseReference mDatabase =FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS);
    private Program program = new Program();
    public void createProgram(Program program){
        FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .push()
                .setValue(program);
    }

    public List<Program> readPrograms(String userID){
        List<Program> programsList = new ArrayList<>();
        Log.d("Program query failuer", userID);
        mDatabase.child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                programsList.clear();
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()){
                    program.setName(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_NAME).getValue().toString());
                    program.setDescription(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_DESCRIPTION).getValue().toString());
                    program.setCategory(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_CATEGORY).getValue().toString());
                    program.setSessionNumber(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_SESSION_NUMBER).getValue().toString());
                    program.setDuration(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_DURATION).getValue().toString());
                    programsList.add(program);
                    Log.d("Meaningful crap dfghkrgkusergus",""+dataSnapshot.child(ProgramConstants.KEY_PROGRAM_NAME).getValue().toString());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                 Log.d("Program query failuer", e.getMessage());

            }
        });
        return programsList;
    }
}

