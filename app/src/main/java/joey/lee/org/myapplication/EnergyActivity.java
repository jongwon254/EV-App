package joey.lee.org.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class EnergyActivity extends AppCompatActivity {

    private TextView tv_today;
    private TextView tv_week;
    private TextView tv_month;

    private Button btn_back;
    private Button btn_battery;
    private Button btn_map;
    private Button btn_dashboard;
    private Button btn_settings;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String userID;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy);

        tv_today = findViewById(R.id.tv_today);
        tv_week = findViewById(R.id.tv_week);
        tv_month = findViewById(R.id.tv_month);

        btn_back = findViewById(R.id.btn_back);
        btn_battery = findViewById(R.id.btn_battery);
        btn_dashboard = findViewById(R.id.btn_dashboard);
        btn_map = findViewById(R.id.btn_map);
        btn_settings = findViewById(R.id.btn_settings);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnergyActivity.this, BatteryActivity.class);
                startActivity(intent);

            }
        });

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnergyActivity.this, JSONActivity.class);
                startActivity(intent);
            }
        });

        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnergyActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnergyActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("users").document(userID).collection("statistics").document("car");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                tv_today.setText(documentSnapshot.getString("today"));
                tv_week.setText(documentSnapshot.getString("week"));
                tv_month.setText(documentSnapshot.getString("month"));
            }
        });
    }
}