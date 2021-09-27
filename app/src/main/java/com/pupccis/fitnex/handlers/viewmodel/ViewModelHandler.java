package com.pupccis.fitnex.handlers.viewmodel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.model.FitnessModel;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.repository.FitnessClassesRepository;
import com.pupccis.fitnex.repository.ProgramsRepository;
import com.pupccis.fitnex.validation.Services.UserValidationService;
import com.pupccis.fitnex.validation.ValidationResult;

import java.util.HashMap;

public class ViewModelHandler {

    public static HashMap<String, Object> onTextChange(String input, Object field){
        UserValidationService userValidationService= new UserValidationService(input, field);
        ValidationResult result = userValidationService.validate();

        HashMap<String, Object> validationData = new HashMap<>();
        validationData.put("validationResult", result);
        validationData.put("field", field);
        return validationData;
    }

    public static FirestoreRecyclerOptions<Program> getFirebaseUIProgramOptions(){
        return new FirestoreRecyclerOptions.Builder<Program>()
                .setQuery(ProgramsRepository.getInstance().readProgramsQuery(), Program.class)
                .build();
    }
    public static FirestoreRecyclerOptions<FitnessModel> getFirebaseUIFitnessClassOptions(){
        return new FirestoreRecyclerOptions.Builder<FitnessModel>()
                .setQuery(FitnessClassesRepository.getInstance().readFitnessClassesQuery(), FitnessModel.class)
                .build();
    }
}
