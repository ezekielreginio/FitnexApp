package com.pupccis.fitnex.Activities.Main.Trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pupccis.fitnex.Models.Class;
import com.pupccis.fitnex.Models.DAO.ClassDAO;
import com.pupccis.fitnex.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class AddClass extends AppCompatActivity implements View.OnClickListener{
    private EditText timeStart, timeEnd, editName, editSessionNumber;
    private TimePickerDialog timePickerDialog;
    private Button addClass;
    private ClassDAO classDAO = new ClassDAO();
    private String timestart, timeend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        editName = (EditText) findViewById(R.id.editTextAddClassName);
        editSessionNumber = (EditText) findViewById(R.id.editTextAddClassSessionNumber);
        timeStart = (EditText) findViewById(R.id.editTextTimeStart);
        timeEnd = (EditText) findViewById(R.id.editTextTimeEnd);
        addClass = (Button) findViewById(R.id.buttonAddClassButton);
        timeStart.setInputType(InputType.TYPE_NULL);
        timeEnd.setInputType(InputType.TYPE_NULL);
        timeStart.setOnClickListener(this);
        timeEnd.setOnClickListener(this);
        addClass.setOnClickListener(this);
    }




    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.editTextTimeStart):
                showTimeDialog(timeStart, 0);
                Toast.makeText(AddClass.this, "time start click", Toast.LENGTH_SHORT).show();
                break;
            case(R.id.editTextTimeEnd):
                Toast.makeText(AddClass.this, "time end click", Toast.LENGTH_SHORT).show();
                showTimeDialog(timeStart, 1);
                break;
            case(R.id.buttonAddClassButton):
                String name = editName.getText().toString();
                String sessionNumber = editSessionNumber.getText().toString();
                Class _class = new Class(name, timestart, timeend, sessionNumber);
                classDAO.createClass(_class);

        }
    }

    private String showTimeDialog(EditText time, int setter) {
        Calendar calendar = Calendar.getInstance();
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
                    timeStart.setText(time);
                    timestart = timeStart.getText().toString();
                }
                else{
                    timeEnd.setText(time);
                    timeend = timeEnd.getText().toString();
                }
            }
        },hours, mins, false);
        timePickerDialog.show();
        return time.toString();
    }

}
