package joey.lee.org.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NotifyActivity extends AppCompatActivity {

    private Button btn_send;
    private EditText text_email;
    private EditText text_message;
    private Button btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        btn_send = findViewById(R.id.btn_send);
        text_email = findViewById(R.id.text_email);
        text_message = findViewById(R.id.text_message);
        btn_back = findViewById(R.id.btn_back);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        text_message.setText("Hey,\n" +
                "I am very interested in your prototype.\n" +
                "Please notify me in case of any further Releases.\n" +
                "Keep up the good work!");

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotifyActivity.this, NotifyActivity2.class);
                startActivity(intent);
            }
        });
    }
}