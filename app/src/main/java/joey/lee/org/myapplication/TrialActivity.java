package joey.lee.org.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TrialActivity extends AppCompatActivity {

    private Button btn_back;
    private Button btn_trial;
    private Button btn_later;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);

        btn_back= findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_trial= findViewById(R.id.btn_trial);
        btn_trial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrialActivity.this, TrialActivity2.class);
                startActivity(intent);
            }
        });

        btn_later= findViewById(R.id.btn_later);
        btn_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrialActivity.this, BatteryActivity_Free.class);
                startActivity(intent);
            }
        });
    }
}