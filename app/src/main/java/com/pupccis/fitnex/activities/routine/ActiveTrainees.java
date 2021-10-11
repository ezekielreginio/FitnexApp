package com.pupccis.fitnex.activities.routine;

import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUIRealtimeRoutineOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.videoconferencing.OutgoingInvitationActivity;
import com.pupccis.fitnex.activities.videoconferencing.VideoActivityDemo;
import com.pupccis.fitnex.activities.videoconferencing.listeners.UsersListener;
import com.pupccis.fitnex.adapters.RealtimeRoutineAdapter;
import com.pupccis.fitnex.databinding.ActivityActiveTraineesBinding;
import com.pupccis.fitnex.handlers.view.WrapContentLinearLayoutManager;
import com.pupccis.fitnex.model.RealtimeRoutine;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.viewmodel.RoutineViewModel;

public class ActiveTrainees extends AppCompatActivity implements UsersListener {
    private ActivityActiveTraineesBinding binding;
    private RealtimeRoutineAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_active_trainees);
        binding.setViewModel(new RoutineViewModel());
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        //Intents
        String programID = getIntent().getStringExtra(ProgramConstants.KEY_PROGRAM_ID);

        FirebaseRecyclerOptions<RealtimeRoutine> options = getFirebaseUIRealtimeRoutineOptions(programID);
        binding.recyclerViewActiveTrainees.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //Instantiate Adapter and Bind to RecyclerView
        adapter = new RealtimeRoutineAdapter(options);
        adapter.setUsersListener(this);
        binding.recyclerViewActiveTrainees.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void initiateVideoMeeting(User user) {
        if(user.getToken() == null || user.getToken().trim().isEmpty()){
            Toast.makeText(ActiveTrainees.this, user.getName()+" is not available for meeting", Toast.LENGTH_SHORT).show();
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

    }
}