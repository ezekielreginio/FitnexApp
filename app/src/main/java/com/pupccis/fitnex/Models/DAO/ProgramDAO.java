package com.pupccis.fitnex.Models.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.pupccis.fitnex.Models.Program;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;

public class ProgramDAO {
    public void createProgram(Program program){
        FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(program);
    }
}
