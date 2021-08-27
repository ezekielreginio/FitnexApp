package com.pupccis.fitnex.Activities.Main.Trainer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pupccis.fitnex.Models.FitnessClass;
import com.pupccis.fitnex.Models.DAO.FitnessClassDAO;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddClass extends AppCompatActivity implements View.OnClickListener{
    private Animation rotateAnimation;
    private ImageView imageView;
    private EditText editTimeStart, editTimeEnd, editName, editSessionNumber, editDescription;
    private TimePickerDialog timePickerDialog;
    private RelativeLayout closeButton;
    private Button addClass;
    private String timeStartHolder, timeEndHolder;
    private FitnessClassDAO fitnessClassDAO = new FitnessClassDAO();
    private Calendar calendar = Calendar.getInstance();
    private FitnessClass fitness_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Extra Intent
        fitness_intent = (FitnessClass) getIntent().getSerializableExtra("fitness");

        setContentView(R.layout.activity_add_class);
        closeButton = (RelativeLayout) findViewById(R.id.relativeLayoutAddClassCloseButton);
        imageView = (ImageView) findViewById(R.id.closeAddClassButton);

        editName = (EditText) findViewById(R.id.editTextAddClassName);
        editSessionNumber = (EditText) findViewById(R.id.editTextAddClassSessionNumber);
        editTimeStart = (EditText) findViewById(R.id.editTextTimeStart);
        editTimeEnd = (EditText) findViewById(R.id.editTextTimeEnd);
        editDescription = (EditText) findViewById(R.id.editTextAddClassDescription);

        editTimeStart.setInputType(InputType.TYPE_NULL);
        editTimeEnd.setInputType(InputType.TYPE_NULL);
        addClass = (Button) findViewById(R.id.buttonAddClassButton);
        editTimeStart.setOnClickListener(this);
        editTimeEnd.setOnClickListener(this);
        addClass.setOnClickListener(this);
        closeButton.setOnClickListener(this);
        rotateAnimation();
        closeButton.setVisibility(View.VISIBLE);

        if(fitness_intent != null){
            editName.setText(fitness_intent.getClassName());
            editSessionNumber.setText(fitness_intent.getSessionNo());
            editTimeStart.setText(fitness_intent.getTimeStart());
            editTimeEnd.setText(fitness_intent.getTimeEnd());
            editDescription.setText(fitness_intent.getDescription());
            addClass.setText("Update Class");
        }
    }

    private void rotateAnimation() {
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageView.setImageResource(R.drawable.ic_close_button);
        imageView.startAnimation(rotateAnimation);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.editTextTimeStart):
                showTimeDialog(editTimeStart, 0);
                Toast.makeText(AddClass.this, "time start click", Toast.LENGTH_SHORT).show();
                break;
            case(R.id.editTextTimeEnd):
                Toast.makeText(AddClass.this, "time end click", Toast.LENGTH_SHORT).show();
                showTimeDialog(editTimeEnd, 1);
                break;
            case(R.id.buttonAddClassButton):
                String name = editName.getText().toString();
                String sessionNo = editSessionNumber.getText().toString();
                String description = editDescription.getText().toString();
                String timeStart = editTimeStart.getText().toString();
                String timeEnd = editTimeEnd.getText().toString();
                Date currentTime = calendar.getTime();
                FitnessClass fitnessClass = new FitnessClass(name, timeStart, timeEnd, sessionNo, currentTime.toString(), description);

                if(fitness_intent != null){

                    fitnessClass.setClassID(fitness_intent.getClassID());
                    fitnessClass.setClassTrainerID(fitness_intent.getClassTrainerID());
                    fitnessClassDAO.updateClass(fitnessClass);
                    closeForm();
                }
                else{
                    fitnessClassDAO.createClass(fitnessClass);
                    closeForm();
                }
                break;
            case(R.id.relativeLayoutAddClassCloseButton):

                break;
        }
    }

    private void closeForm() {
        startActivity(new Intent(AddClass.this, TrainerDashboard.class));
        overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
    }

    private String showTimeDialog(EditText time, int setter) {

        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int mins = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddClass.this, R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, i);
                c.set(Calendar.MINUTE, i1);
                c.setTimeZone(TimeZone.getDefault());
                SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                String time = format.format(c.getTime());
                if (setter ==0){
                    editTimeStart.setText(time);
                    timeStartHolder = editTimeStart.getText().toString();
                }
                else{
                    editTimeEnd.setText(time);
                    timeEndHolder = editTimeEnd.getText().toString();
                }
            }
        },hours, mins, false);
        timePickerDialog.show();
        return time.toString();
    }

}
