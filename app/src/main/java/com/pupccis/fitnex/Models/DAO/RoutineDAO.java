package com.pupccis.fitnex.Models.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pupccis.fitnex.Models.Program;
import com.pupccis.fitnex.Models.Routine;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.Utilities.Constants.RoutineConstants;

import java.util.HashMap;

public class RoutineDAO {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS);

    public void createRoutine(Routine routine, Program program){
        mDatabase
                .child(program.getProgramID())
                .child(RoutineConstants.KEY_COLLECTION_ROUTINE)
                .push()
                .setValue(routine);
    }

}
