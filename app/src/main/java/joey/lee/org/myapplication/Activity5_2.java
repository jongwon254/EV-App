package joey.lee.org.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity5_2 extends AppCompatActivity {

    private Button btn_back;
    private Button btn_checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity5_2);

        btn_back = findViewById(R.id.btn_back);
        btn_checkout = findViewById(R.id.btn_checkout);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCarConnection();
            }
        });
    }



    // go to car connection activity
    public void openCarConnection() {
        Intent intent = new Intent(this, Activity5_3.class);
        startActivity(intent);
    }
}