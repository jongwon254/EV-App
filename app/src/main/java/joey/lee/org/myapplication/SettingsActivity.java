package joey.lee.org.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    private Button btn_profile;
    private Button btn_car;
    private Button btn_refer;
    private Button btn_favorites;
    private Button btn_contact;
    private Button btn_logout;

    private Button btn_dashboard;
    private Button btn_map;
    private Button btn_battery;
    private Button btn_energy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn_battery = findViewById(R.id.btn_battery);
        btn_dashboard = findViewById(R.id.btn_dashboard);
        btn_map = findViewById(R.id.btn_map);
        btn_energy = findViewById(R.id.btn_energy);

        btn_energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, EnergyActivity.class);
                startActivity(intent);
            }
        });

        btn_battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, BatteryActivity.class);
                startActivity(intent);

            }
        });

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, JSONActivity.class);
                startActivity(intent);
            }
        });

        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });



        btn_car = findViewById(R.id.btn_car);
        btn_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, CarConnection2.class);
                startActivity(intent);
            }
        });

        btn_profile = findViewById(R.id.btn_profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, Activity4.class);
                startActivity(intent);
            }
        });

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_refer = findViewById(R.id.btn_refer);
        btn_refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ReferActivity.class);
                startActivity(intent);
            }
        });

        btn_contact = findViewById(R.id.btn_contact);
        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        btn_favorites = findViewById(R.id.btn_favorites);
        btn_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(SettingsActivity.this, FavoritesActivity.class);
               startActivity(intent);
            }
        });
    }
}