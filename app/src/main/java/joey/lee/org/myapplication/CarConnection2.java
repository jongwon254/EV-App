package joey.lee.org.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class CarConnection2 extends AppCompatActivity {

    private Button btn_back;
    private Button btn_login;

    private EditText text_email;
    private EditText text_password;

    private Switch switch_login;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_connection2);

        btn_back = findViewById(R.id.btn_back);
        btn_login = findViewById(R.id.btn_login);

        switch_login = findViewById(R.id.switch_login);

        text_email = findViewById(R.id.text_email);
        text_password = findViewById(R.id.text_password);

        email = text_email.getText().toString();
        password = text_password.getText().toString();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCarConnection3();
            }
        });
    }

    // go to car connection activity
    public void openCarConnection3() {
        Toast.makeText(CarConnection2.this,"Login successful", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, CarConnection3.class);
        startActivity(intent);
    }
}