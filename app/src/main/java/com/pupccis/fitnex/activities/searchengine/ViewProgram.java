package com.pupccis.fitnex.activities.searchengine;

import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUIProgramRatingOptions;
import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUISearchProgramOptions;
import static com.pupccis.fitnex.utilities.Constants.PatronConstants.getPrivilegeHashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.patron.TrainerPatronPage;
import com.pupccis.fitnex.activities.routine.RoutinePage;
import com.pupccis.fitnex.adapters.ProgramRatingAdapter;
import com.pupccis.fitnex.api.enums.Privilege;
import com.pupccis.fitnex.databinding.ActivityViewProgramBinding;
import com.pupccis.fitnex.handlers.view.WrapContentLinearLayoutManager;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.model.ProgramRating;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.viewmodel.PatronViewModel;
import com.pupccis.fitnex.viewmodel.ProgramViewModel;

import java.util.HashMap;

public class ViewProgram extends AppCompatActivity implements View.OnClickListener{
    private ActivityViewProgramBinding binding;
    private Program program_intent;
    private ProgramRatingAdapter adapter;
    private ProgramViewModel viewModel = new ProgramViewModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_program);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.setPresenter(this);
        binding.executePendingBindings();

        program_intent = (Program) getIntent().getSerializableExtra("program");

        binding.textViewProgramName.setText(program_intent.getName());
        binding.textViewProgramDescription.setText(program_intent.getDescription());


        FirestoreRecyclerOptions<ProgramRating> options = getFirebaseUIProgramRatingOptions(program_intent.getProgramID());
        binding.recyclerViewUserRating.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ProgramRatingAdapter(options);
        binding.recyclerViewUserRating.setAdapter(adapter);
        adapter.startListening();


    }

    @Override
    protected void onStart() {
        Log.d("On Start", "Triggered");
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onClick(View view) {
        if(view == binding.buttonAvailProgram){
            Log.e("clicked", "subscribe");
            Privilege privilege = Privilege.valueOf(program_intent.getPrivilege());

            if(privilege == Privilege.FREE){
                Intent intent = new Intent(ViewProgram.this, RoutinePage.class);
                intent.putExtra("program", program_intent);
                startActivity(intent);
            }
            else{
                new PatronViewModel().checkPatronStatus(program_intent.getTrainerID()).observe(binding.getLifecycleOwner(), new Observer<Privilege>() {
                    @Override
                    public void onChanged(Privilege privilege) {
                        if(privilege != null){
                            if(privilege == Privilege.NONE){
                                showSubscriptionModal();
                            }
                            else{
                                HashMap<Privilege, Integer> privilegeMap = getPrivilegeHashMap();
                                int currentSubscription = privilegeMap.get(privilege);
                                int programPrivilege = privilegeMap.get(Privilege.valueOf(program_intent.getPrivilege()));
                                if(currentSubscription >= programPrivilege){
                                    Intent intent = new Intent(ViewProgram.this, RoutinePage.class);
                                    intent.putExtra("program", program_intent);
                                    startActivity(intent);
                                }
                                else{
                                    showInvalidSubscriptionModal();
                                }
                            }
                        }
                    }


                });
            }
        }
        else if(view == binding.buttonSubscribeToPatron){
            binding.constraintLayoutUserNotSubscribedModal.setVisibility(View.GONE);
            Intent intent = new Intent(ViewProgram.this, TrainerPatronPage.class);
            intent.putExtra(ProgramConstants.KEY_MODEL_PROGRAM, program_intent);
            startActivity(intent);
        }
    }

    private void showInvalidSubscriptionModal() {
        binding.textViewSubscriptionModalTitle.setText("Invalid Subscription");
        binding.textViewSubscriptionModalInfo.setText("You current subscription is not valid to view this program");
        binding.buttonSubscribeToPatron.setText("Upgrade Subscription");
        binding.constraintLayoutUserNotSubscribedModal.setVisibility(View.VISIBLE);
    }

    private void showSubscriptionModal() {
        binding.textViewSubscriptionModalTitle.setText("User Not Subscribed");
        binding.textViewSubscriptionModalInfo.setText("The program that you are trying to view is exclusive for patron subscribers");
        binding.buttonSubscribeToPatron.setText("Subscribe to View");
        binding.constraintLayoutUserNotSubscribedModal.setVisibility(View.VISIBLE);
    }
}