package joey.lee.org.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EnterCost extends AppCompatActivity {

    private Button btn_back;
    private Button btn_save;
    private Button btn_battery;
    private Button btn_map;
    private Button btn_energy;
    private Button btn_settings;

    private EditText text_date;
    private EditText text_time;
    private EditText text_cost;
    private EditText text_kwh;
    private EditText text_location;

    private String date;
    private String time;
    private String cost;
    private String kwh;
    private String location;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    // Authentication Server and Database
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String userID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_cost);

        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);

        // Getting information from EditText Fields
        text_date= findViewById(R.id.text_date);
        text_time = findViewById(R.id.text_time);
        text_cost = findViewById(R.id.text_cost);
        text_kwh = findViewById(R.id.text_kwh);
        text_location = findViewById(R.id.text_location);;

        // Instance of Authentication Server and Database
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btn_energy = findViewById(R.id.btn_energy);
        btn_energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterCost.this, EnergyActivity.class);
                startActivity(intent);
            }
        });

        btn_battery = findViewById(R.id.btn_battery);
        btn_battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterCost.this, BatteryActivity.class);
                startActivity(intent);
            }
        });

        btn_map = findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterCost.this, JSONActivity.class);
                startActivity(intent);
            }
        });

        btn_settings = findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterCost.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        text_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EnterCost.this, android.R.style.Theme_DeviceDefault_Light_Dialog, dateSetListener, year, month, day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        text_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(EnterCost.this, android.R.style.Theme_DeviceDefault_Light_Dialog, timeSetListener, hour, min, android.text.format.DateFormat.is24HourFormat(EnterCost.this));
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "." + month + "." + year;
                text_date.setText(date);

            }
        };

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String time = i + ":" + i1;
                text_time.setText(time);
            }
        };

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // store all information
                date = text_date.getText().toString();
                time = text_time.getText().toString();
                cost = text_cost.getText().toString();
                kwh = text_kwh.getText().toString();
                location = text_location.getText().toString();

                // requirements of text fields
                if(date.isEmpty()) {
                    text_date.setError("Please enter the date");
                    text_date.requestFocus();
                } else if(time.isEmpty()) {
                    text_time.setError("Please enter the time");
                    text_time.requestFocus();
                } else if(cost.isEmpty()) {
                    text_cost.setError("Please enter the cost");
                    text_cost.requestFocus();
                } else if(kwh.isEmpty()) {
                    text_kwh.setError("Please enter the kWh");
                    text_kwh.requestFocus();
                } else if((date.isEmpty() && time.isEmpty() && cost.isEmpty() && kwh.isEmpty() && location.isEmpty())) {
                    Toast.makeText(EnterCost.this, "Please enter your information!", Toast.LENGTH_SHORT).show();
                } else if(!(date.isEmpty() && time.isEmpty() && cost.isEmpty() && kwh.isEmpty() && location.isEmpty())) {

                    // Firestore Database to store personal information
                    userID = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = db.collection("users").document(userID).collection("costs").document(String.valueOf(System.currentTimeMillis()));

                    Map<String, Object> costs = new HashMap<>();
                    costs.put("date", date);
                    costs.put("time", time);
                    costs.put("cost", cost);
                    costs.put("kwh", kwh);
                    costs.put("location", location);

                    documentReference.set(costs).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EnterCost.this,"Entry saved", Toast.LENGTH_SHORT).show();
                        }
                    });

                    startActivity(new Intent(EnterCost.this, DashboardActivity.class));

                } else {
                    Toast.makeText(EnterCost.this,"Error Occurred :(", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

}