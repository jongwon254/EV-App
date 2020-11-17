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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Arrays;
import java.util.List;

public class BatteryActivity_Free extends AppCompatActivity {

    // Buttons
    private Button btn_back;
    private Button btn_dashboard;
    private Button btn_map;
    private Button btn_settings;
    private Button btn_energy;

    private AdView ad_View2;

    private ImageView view_battery;

    private TextView tv_battery;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery__free);

        //ad version start ----------------

        //Banner1 ca-app-pub-5507911385347863/9227311693
        //Banner2 ca-app-pub-5507911385347863/9184253127
        //Interstitial ca-app-pub-5507911385347863/7610800082

        //Test Banner ca-app-pub-3940256099942544/6300978111
        //Test Interstitial ca-app-pub-3940256099942544/1033173712

        List<String> testDeviceIds = Arrays.asList("7E7D0E82B6D512E78E70E56DC3DF0923");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });


        ad_View2 = findViewById(R.id.ad_view2);

        AdRequest adRequest = new AdRequest.Builder().build();
        adRequest.isTestDevice(this);
        ad_View2.loadAd(adRequest);


        // Ad version end ----------------

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
                Intent intent = new Intent(BatteryActivity_Free.this, DashboardActivity_Free.class);
                startActivity(intent);
            }
        });

        btn_map = findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BatteryActivity_Free.this, GoogleMap_Free.class);
                startActivity(intent);
            }
        });

        btn_energy = findViewById(R.id.btn_energy);
        btn_energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BatteryActivity_Free.this, EnergyActivity_Free.class);
                startActivity(intent);
            }
        });

        btn_settings = findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(BatteryActivity.this, SettingsActivity.class);
                //startActivity(intent);
            }
        });


        tv_battery = findViewById(R.id.tv_battery);

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