package joey.lee.org.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class BatteryActivity extends AppCompatActivity {

    // Buttons
    private Button btn_back;
    private Button btn_dashboard;
    private Button btn_map;
    private Button btn_settings;
    private Button btn_energy;
    private Button btn_lifetime;
    private ImageView view_battery;

    private TextView tv_battery;
    private TextView tv_temp;
    private TextView tv_capacity;
    private TextView tv_status;
    private TextView tv_lifetime;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_dashboard = findViewById(R.id.btn_dashboard);
        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BatteryActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        btn_map = findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BatteryActivity.this, JSONActivity.class);
                startActivity(intent);
            }
        });

        btn_energy = findViewById(R.id.btn_energy);
        btn_energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BatteryActivity.this, EnergyActivity.class);
                startActivity(intent);
            }
        });

        btn_settings = findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BatteryActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        btn_lifetime = findViewById(R.id.btn_lifetime);
        btn_lifetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BatteryActivity.this, Pop_Energy.class);
                startActivity(intent);
            }
        });


        tv_battery = findViewById(R.id.tv_battery);
        tv_capacity = findViewById(R.id.tv_capacity);
        tv_lifetime = findViewById(R.id.tv_lifetime);
        tv_temp = findViewById(R.id.tv_temp);
        tv_status = findViewById(R.id.tv_status);
        view_battery = findViewById(R.id.view_battery);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("users").document(userID).collection("statistics").document("car");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                tv_battery.setText(documentSnapshot.getString("battery") + "%");
                int battery = Integer.parseInt(removeLastChar(tv_battery.getText().toString()));
                if(battery >= 75) {
                    tv_battery.setTextColor(Color.parseColor("#3cdb2e"));
                    tv_battery.setText(documentSnapshot.getString("battery") + "%");
                    view_battery.setBackgroundResource(R.drawable.energy_full);
                } else if(battery >= 30) {
                    tv_battery.setTextColor(Color.parseColor("#e6b122"));
                    tv_battery.setText(documentSnapshot.getString("battery") + "%");
                    view_battery.setBackgroundResource(R.drawable.energy_middle);

                } else {
                    tv_battery.setTextColor(Color.parseColor("#bf1b2e"));
                    tv_battery.setText(documentSnapshot.getString("battery") + "%");
                    view_battery.setBackgroundResource(R.drawable.energy_low);
                }


                tv_capacity.setText(documentSnapshot.getString("capacity") + "%");
                int cap = Integer.parseInt(removeLastChar(tv_capacity.getText().toString()));
                if(cap >= 75) {
                    tv_capacity.setTextColor(Color.parseColor("#3cdb2e"));
                    tv_capacity.setText(documentSnapshot.getString("capacity") + "%");
                } else if(cap >= 30) {
                    tv_capacity.setTextColor(Color.parseColor("#e6b122"));
                    tv_capacity.setText(documentSnapshot.getString("capacity") + "%");
                } else {
                    tv_capacity.setTextColor(Color.parseColor("#bf1b2e"));
                    tv_capacity.setText(documentSnapshot.getString("capacity") + "%");
                }


                tv_lifetime.setText(documentSnapshot.getString("lifetime"));
                tv_status.setText(documentSnapshot.getString("status"));

                tv_temp.setText(documentSnapshot.getString("temperature") + "°C");
                int temp = Integer.parseInt(removeLastChars(tv_temp.getText().toString(), 2));
                if(temp >= 15 && temp <= 35) {
                    tv_temp.setTextColor(Color.parseColor("#3cdb2e"));
                    tv_temp.setText(documentSnapshot.getString("temperature") + "°C");
                } else if(temp < 15) {
                    tv_temp.setTextColor(Color.parseColor("#5cb6ed"));
                    tv_temp.setText(documentSnapshot.getString("temperature") + "°C");
                    tv_status.setText("Too Cold");
                } else {
                    tv_temp.setTextColor(Color.parseColor("#bf1b2e"));
                    tv_temp.setText(documentSnapshot.getString("temperature") + "°C");
                    tv_status.setText("Too Hot");
                }


            }
        });


    }

    public static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }

    public static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }
}