package joey.lee.org.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.sql.Ref;

public class ReferActivity extends AppCompatActivity {

    private Button btn_share;
    private Button btn_back;
    private Button btn_battery;
    private Button btn_map;
    private Button btn_energy;
    private Button btn_dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);

        btn_energy = findViewById(R.id.btn_energy);
        btn_energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReferActivity.this, EnergyActivity.class);
                startActivity(intent);
            }
        });

        btn_battery = findViewById(R.id.btn_battery);
        btn_battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReferActivity.this, BatteryActivity.class);
                startActivity(intent);
            }
        });

        btn_map = findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReferActivity.this, JSONActivity.class);
                startActivity(intent);
            }
        });

        btn_dashboard = findViewById(R.id.btn_dashboard);
        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReferActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });


        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_share = findViewById(R.id.btn_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                //String shareTitle = "Hey, I am using Electricity For Future!\n";
                String shareBody = "Hey, I am using Electricity For Future!\n" +
                        "You should check it out, just click on this link: https://electricityforfuture.wixsite.com/release\n" +
                        "\nYou can use my code to get up to 3 months of premium!\n" +
                        "Code: DX31DR3";
               // intent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Share Electricity For Future With"));
            }
        });
    }
}