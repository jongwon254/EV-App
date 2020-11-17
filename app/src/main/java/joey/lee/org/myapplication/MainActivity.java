package joey.lee.org.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Arrays;
import java.util.List;

// Home Activity
public class MainActivity extends AppCompatActivity {

    // Buttons
    private Button btn_login;
    private Button btn_register;
    private Button btn_google;
    private Button btn_notify;

    private Button btn_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleMap googleMap = new GoogleMap();
        googleMap.setRestart(0);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // Instance for buttons
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);
        btn_google = findViewById(R.id.btn_google);
        btn_notify = findViewById(R.id.btn_notify);

        btn_admin= findViewById(R.id.btn_admin);

        // Button click
        btn_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(MainActivity.this, NotifyActivity.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                openActivity2();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                openActivity3();
            }
        });

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoogleLogin();
            }
        });

        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdmin();
            }
        });
    }

    // go to register activity for register button
    public void openActivity2() {
        Intent intent = new Intent(this, Activity2.class); //Activity2
        startActivity(intent);
    }

    // go to login activity for login button
    public void openActivity3() {
        Intent intent = new Intent(this, Activity3.class); //Activity3
        startActivity(intent);
    }

    // go to google login
    public void openGoogleLogin() {
        Intent intent = new Intent(this, GoogleLogin.class);
        startActivity(intent);
    }

    // ADMIN BUTTON DELETE!
    public void openAdmin() {
        Intent intent = new Intent(this, EnergyActivity.class);
        startActivity(intent);
    }

}