package joey.lee.org.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CarConnection extends AppCompatActivity {

    private Button btn_back;
    private Button btn_connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_connection);

        btn_back = findViewById(R.id.btn_back);
        btn_connect = findViewById(R.id.btn_connect);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCarConnection2();
            }
        });
    }

    // go to login activity for back button
    public void openActivity3() {
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }

    // go to car connection activity
    public void openCarConnection2() {
        Intent intent = new Intent(this, CarConnection2.class);
        startActivity(intent);
    }
}