package com.pupccis.fitnex.handlers.viewmodel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.model.Routine;
import com.pupccis.fitnex.repository.FitnessClassesRepository;
import com.pupccis.fitnex.repository.ProgramsRepository;
import com.pupccis.fitnex.repository.RoutinesRepository;
import com.pupccis.fitnex.validation.Services.UserValidationService;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.validation.validationFields.RegistrationFields;

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
    public static FirestoreRecyclerOptions<FitnessClass> getFirebaseUIFitnessClassOptions(){
        return new FirestoreRecyclerOptions.Builder<FitnessClass>()
                .setQuery(FitnessClassesRepository.getInstance().readFitnessClassesQuery(), FitnessClass.class)
                .build();
    }
    public static FirestoreRecyclerOptions<Routine> getFirebaseUIRoutineOptions(String program_id){
        return new FirestoreRecyclerOptions.Builder<Routine>()
                .setQuery(RoutinesRepository.getInstance().getRoutinesQuery(program_id), Routine.class)
                .build();
    }
}
