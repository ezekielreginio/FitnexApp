package com.pupccis.fitnex.Models.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pupccis.fitnex.Models.Class;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;

public class ClassDAO {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS);

    public void createClass(Class _class){
        FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .push()
                .setValue(_class);
    }
}
